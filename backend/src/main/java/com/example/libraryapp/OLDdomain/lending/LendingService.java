package com.example.libraryapp.OLDdomain.lending;

import com.example.libraryapp.NEWapplication.message.MessageFacade;
import com.example.libraryapp.OLDdomain.action.ActionService;
import com.example.libraryapp.OLDdomain.action.types.ActionBookBorrowed;
import com.example.libraryapp.OLDdomain.action.types.ActionBookLost;
import com.example.libraryapp.OLDdomain.action.types.ActionBookRenewed;
import com.example.libraryapp.OLDdomain.action.types.ActionBookReturned;
import com.example.libraryapp.OLDdomain.bookItem.BookItem;
import com.example.libraryapp.NEWdomain.bookItem.model.BookItemStatus;
import com.example.libraryapp.OLDdomain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.OLDdomain.exception.lending.CheckoutException;
import com.example.libraryapp.OLDdomain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.OLDdomain.exception.payment.UnsettledFineException;
import com.example.libraryapp.OLDdomain.lending.assembler.LendingModelAssembler;
import com.example.libraryapp.OLDdomain.lending.dto.LendingDto;
import com.example.libraryapp.OLDdomain.member.Member;
import com.example.libraryapp.OLDdomain.notification.NotificationService;
import com.example.libraryapp.OLDdomain.notification.types.*;
import com.example.libraryapp.OLDdomain.reservation.Reservation;
import com.example.libraryapp.OLDdomain.reservation.ReservationService;
import com.example.libraryapp.OLDdomain.reservation.ReservationStatus;
import com.example.libraryapp.OLDdomain.reservation.dto.ReservationResponse;
import com.example.libraryapp.OLDmanagement.ActionRequest;
import com.example.libraryapp.OLDmanagement.Constants;
import com.example.libraryapp.OLDmanagement.FineService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LendingService {
    private final MessageFacade messageFacade;
    private final LendingRepository lendingRepository;
//    private final BookItemRepository bookItemRepository;
    private final ReservationService reservationService;
    private final ActionService actionService;
    private final FineService fineService;
    private final NotificationService notificationService;
    private final LendingModelAssembler lendingModelAssembler;
    private final PagedResourcesAssembler<Lending> pagedResourcesAssembler;

    public PagedModel<LendingDto> findLendings(
            Long memberId, LendingStatus status, Pageable pageable, Boolean renewable
    ) {
        List<Lending> lendings = findLendingsByMemberId(memberId).stream()
                .filter(len -> status == null || len.getStatus() == status)
                .filter(len -> renewable == null || isRenewable(len) == renewable)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lendings.size());
        List<Lending> paginatedList = lendings.subList(start, end);
        Page<Lending> checkoutDtoPage = new PageImpl<>(paginatedList, pageable, lendings.size());
        return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
    }

//    @Transactional
//    public LendingDto borrowABook(ActionRequest request) {
//        Reservation reservation = reservationService.findReservation(request.getMemberId(), request.getBookBarcode(), ReservationStatus.READY);
//        Member member = reservation.getMember();
//        BookItem book = reservation.getBookItem();
//        checkIfMemberCanBorrowABook(member);
//        Lending lendingToSave = createLendingToSave(reservation);
//        Lending savedLending = lendingRepository.save(lendingToSave);
//        member.addLoanedItemId(savedLending.getId());
//        LendingDto savedLendingDto = LendingDtoMapper.map(savedLending);
//        book.updateAfterLending(savedLending.getCreationDate(), savedLending.getDueDate());
//        updateMemberAfterLending(member);
//
//        reservation.setStatus(ReservationStatus.COMPLETED);
//        actionService.save(new ActionBookBorrowed(savedLendingDto));
//        notificationService.sendToUser(new NotificationBookBorrowed(savedLendingDto), savedLendingDto.getMember());
//        return lendingModelAssembler.toModel(savedLending);
    }

//    @Transactional
//    public LendingDto renewABook(String bookBarcode) {
//        Lending lending = findLendingByBookBarcode(bookBarcode);
//        LendingDto lendingDto = LendingDtoMapper.map(lending);
//        checkIfLendingCanBeRenewed(lending);
//        lending.updateAfterRenewing();
//        actionService.save(new ActionBookRenewed(lendingDto));
//        notificationService.sendToUser(new NotificationBookRenewed(lendingDto), lendingDto.getMember());
//        return lendingModelAssembler.toModel(lending);
//    }

//    @Transactional
//    public LendingDto returnABook(String bookBarcode) {
//        Lending lending = findLendingByBookBarcode(bookBarcode);
//        LendingDto lendingDto = LendingDtoMapper.map(lending);
//        lending.setReturnDate(LocalDate.now());
//        BookItem book = lending.getBookItem();
//        Member member = lending.getMember();
//        member.removeLoanedItemId(lending.getId());
//        lending.updateAfterReturning();



//        BigDecimal fine = fineService.countFine(lending.getDueDate(), lending.getReturnDate());
//        book.updateAfterReturning(lending.getReturnDate(), reservationService.isBookReserved(book.getId()));
//        member.updateAfterReturning(fine);
//        actionService.save(new ActionBookReturned(lendingDto));
//        notificationService.sendToUser(new NotificationBookReturned(lendingDto), lendingDto.getMember());
//        reservationService.findAllPendingReservationsByBookItemId(book.getId()).stream()
//                .min(Comparator.comparing(ReservationResponse::getCreationDate))
//                .ifPresent(res ->
//                        notificationService.sendToUser(new NotificationBookAvailableToBorrow(res),
//                        res.getMember())
//                );
//        return lendingModelAssembler.toModel(lending);
//    }

    @Transactional
    public LendingDto setLendingLost(Long lendingId) {
        Lending lending = findLending(lendingId);
        LendingDto lendingDto = LendingDtoMapper.map(lending);
        lending.setReturnDate(LocalDate.now());
        BookItem book = lending.getBookItem();
        Member member = lending.getMember();
        member.removeLoanedItemId(lending.getId());
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
//        BookItem bookItem = bookItemRepository.findByBarcode(bookBarcode)
//                .orElseThrow(() -> new BookItemNotFoundException(bookBarcode));
//
//        return lendingRepository.findCurrentLendingByBookItemId(bookItem.getId())
//                .orElseThrow(() -> new LendingNotFoundException(bookBarcode));
        return null;
    }

    private List<Lending> findLendingsByMemberId(Long memberId) {
        return lendingRepository.findAll().stream()
                .filter(len -> memberId == null || Objects.equals(len.getMember().getId(), memberId))
                .toList();
    }

    private Lending findLending(Long id) {
        return lendingRepository.findById(id)
                .orElseThrow(() -> new LendingNotFoundException(id));
    }

    private boolean isRenewable(Lending len) {
        return !reservationService.isBookReserved(len.getBookItem().getId())
             && len.getDueDate().isAfter(LocalDate.now());
    }



    private void checkIfLendingCanBeRenewed(Lending lending) {
        boolean bookIsReserved = reservationService.isBookReserved(lending.getBookItem().getId());
        if (bookIsReserved || lending.isAfterDueDate()) {
            throw new CheckoutException("Message.LENDING_RENEWAL_FAILED.getMessage()");
        }
    }

    private void updateMemberAfterLending(Member member) {
        member.updateAfterLending();
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