package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.action.ActionRepository;
import com.example.libraryapp.domain.action.ActionService;
import com.example.libraryapp.domain.action.types.ActionBookBorrowed;
import com.example.libraryapp.domain.action.types.ActionBookLost;
import com.example.libraryapp.domain.action.types.ActionBookRenewed;
import com.example.libraryapp.domain.action.types.ActionBookReturned;
import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemRepository;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.config.assembler.LendingModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.lending.CheckoutException;
import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.exception.payment.UnsettledFineException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.notification.types.*;
import com.example.libraryapp.domain.reservation.*;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Constants;
import com.example.libraryapp.management.FineService;
import com.example.libraryapp.management.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LendingService {
    private final LendingRepository lendingRepository;
    private final BookItemRepository bookItemRepository;
    private final ReservationService reservationService;
    private final ActionService actionService;
    private final FineService fineService;
    private final NotificationService notificationService;
    private final LendingModelAssembler lendingModelAssembler;
    private final PagedResourcesAssembler<Lending> pagedResourcesAssembler;

    public PagedModel<LendingDto> findLendings(
            Long memberId, LendingStatus status, Pageable pageable, Boolean renewable
    ) {
        List<Lending> lendings = lendingRepository.findAll().stream()
                .filter(len -> memberId == null || Objects.equals(len.getMember().getId(), memberId))
                .filter(len -> status == null || len.getStatus() == status)
                .filter(len -> renewable == null || isRenewable(len) == renewable)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lendings.size());
        List<Lending> paginatedList = lendings.subList(start, end);
        Page<Lending> checkoutDtoPage = new PageImpl<>(paginatedList, pageable, lendings.size());
        return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
    }

    public LendingDto findLendingById(Long id) {
        Lending lending = findLending(id);
        return lendingModelAssembler.toModel(lending);
    }

    @Transactional
    public LendingDto borrowABook(ActionRequest request) {
        Reservation reservation = reservationService.findReservation(request.getMemberId(), request.getBookBarcode(), ReservationStatus.READY);
        Member member = reservation.getMember();
        BookItem book = reservation.getBookItem();
        checkIfMemberCanBorrowABook(member);
        Lending lendingToSave = createLendingToSave(reservation);
        Lending savedLending = lendingRepository.save(lendingToSave);
        LendingDto savedLendingDto = LendingDtoMapper.map(savedLending);
        book.updateAfterLending(savedLending.getCreationDate(), savedLending.getDueDate());
        member.updateAfterLending();
        reservation.setStatus(ReservationStatus.COMPLETED);
        actionService.save(new ActionBookBorrowed(savedLendingDto));
        notificationService.sendToUser(new NotificationBookBorrowed(savedLendingDto), savedLendingDto.getMember());
        return lendingModelAssembler.toModel(savedLending);
    }

    @Transactional
    public LendingDto renewABook(String bookBarcode) {
        Lending lending = findLendingByBookBarcode(bookBarcode);
        LendingDto lendingDto = LendingDtoMapper.map(lending);
        checkIfLendingCanBeRenewed(lending);
        lending.updateAfterRenewing();
        actionService.save(new ActionBookRenewed(lendingDto));
        notificationService.sendToUser(new NotificationBookRenewed(lendingDto), lendingDto.getMember());
        return lendingModelAssembler.toModel(lending);
    }

    @Transactional
    public LendingDto returnABook(String bookBarcode) {
        Lending lending = findLendingByBookBarcode(bookBarcode);
        LendingDto lendingDto = LendingDtoMapper.map(lending);
        lending.setReturnDate(LocalDate.now());
        BookItem book = lending.getBookItem();
        Member member = lending.getMember();
        BigDecimal fine = fineService.countFine(lending.getDueDate(), lending.getReturnDate());
        lending.updateAfterReturning();
        book.updateAfterReturning(lending.getReturnDate(), reservationService.isBookReserved(book.getId()));
        member.updateAfterReturning(fine);
        actionService.save(new ActionBookReturned(lendingDto));
        notificationService.sendToUser(new NotificationBookReturned(lendingDto), lendingDto.getMember());
        return lendingModelAssembler.toModel(lending);
    }

    @Transactional
    public LendingDto setLendingLost(Long lendingId) {
        Lending lending = findLending(lendingId);
        LendingDto lendingDto = LendingDtoMapper.map(lending);
        lending.setReturnDate(LocalDate.now());
        BookItem book = lending.getBookItem();
        Member member = lending.getMember();
        BigDecimal fine = fineService.countFine(lending.getDueDate(), lending.getReturnDate());
        fine = fine.add(book.getPrice());
        lending.setStatus(LendingStatus.COMPLETED);
        book.setStatus(BookItemStatus.LOST);
        member.updateAfterReturning(fine);
        actionService.save(new ActionBookLost(lendingDto));
        notificationService.sendToUser(new NotificationBookLost(lendingDto), lendingDto.getMember());
        reservationService.cancelReservations(book.getId());
        return lendingModelAssembler.toModel(lending);
    }

    private Lending findLendingByBookBarcode(String bookBarcode) {
        BookItem bookItem = bookItemRepository.findByBarcode(bookBarcode)
                .orElseThrow(() -> new BookItemNotFoundException(bookBarcode));

        return lendingRepository.findCurrentLendingByBookItemId(bookItem.getId())
                .orElseThrow(() -> new LendingNotFoundException(bookBarcode));
    }

    private Lending findLending(Long id) {
        return lendingRepository.findById(id)
                .orElseThrow(() -> new LendingNotFoundException(id));
    }

    private boolean isRenewable(Lending len) {
        return !reservationService.isBookReserved(len.getBookItem().getId())
             && len.getDueDate().isAfter(LocalDate.now());
    }

    private void checkIfMemberCanBorrowABook(Member member) {
        if (member.hasCharges()) {
            throw new UnsettledFineException();
        }
        if (member.getTotalBooksBorrowed() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            throw new CheckoutException(Message.LENDING_LIMIT_EXCEEDED);
        }
    }

    private void checkIfLendingCanBeRenewed(Lending lending) {
        boolean bookIsReserved = reservationService.isBookReserved(lending.getBookItem().getId());
        if (bookIsReserved || lending.isAfterDueDate()) {
            throw new CheckoutException(Message.LENDING_CANNOT_BE_RENEWED);
        }
    }

    private Lending createLendingToSave(Reservation reservation) {
        LocalDate startTime = LocalDate.now();
        LocalDate endTime = startTime.plusDays(Constants.MAX_LENDING_DAYS);
        Lending lendingToSave = new Lending();
        lendingToSave.setMember(reservation.getMember());
        lendingToSave.setBookItem(reservation.getBookItem());
        lendingToSave.setCreationDate(startTime);
        lendingToSave.setDueDate(endTime);
        lendingToSave.setStatus(LendingStatus.CURRENT);
        return lendingToSave;
    }
}