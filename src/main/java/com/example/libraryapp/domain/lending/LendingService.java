package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.config.assembler.LendingModelAssembler;
import com.example.libraryapp.domain.exception.fine.UnsettledFineException;
import com.example.libraryapp.domain.exception.lending.CheckoutException;
import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.fine.FineService;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.reservation.ReservationRepository;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.management.Constants;
import com.example.libraryapp.management.Message;
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
import java.util.Optional;

@Service
public class LendingService {
    private final LendingRepository lendingRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final FineService fineService;
    private final LendingModelAssembler lendingModelAssembler;
    private final PagedResourcesAssembler<Lending> pagedResourcesAssembler;

    public LendingService(LendingRepository lendingRepository,
                          MemberRepository memberRepository,
                          ReservationRepository reservationRepository,
                          FineService fineService,
                          LendingModelAssembler lendingModelAssembler,
                          PagedResourcesAssembler<Lending> pagedResourcesAssembler) {
        this.lendingRepository = lendingRepository;
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
        this.fineService = fineService;
        this.lendingModelAssembler = lendingModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<LendingDto> findLendings(Long memberId, Pageable pageable) {
        Optional<Member> optMember = memberRepository.findById(memberId);
        PagedModel<LendingDto> collectionModel;
        if (optMember.isPresent()) {
            collectionModel = findLendingsByMemberId(memberId, pageable);
        } else {
            collectionModel = findAllCheckouts(pageable);
        }
        return collectionModel;
    }

    public LendingDto findLendingById(Long id) {
        Lending lending = findLending(id);
        return lendingModelAssembler.toModel(lending);
    }

    @Transactional
    public LendingDto borrowABook(Long memberId, String bookBarcode) {
        Reservation reservation = findReservation(memberId, bookBarcode);
        Member member = reservation.getMember();
        BookItem book = reservation.getBookItem();
        checkIfMemberCanBorrowABook(member);
        Lending lendingToSave = createLendingToSave(reservation);
        Lending savedLending = lendingRepository.save(lendingToSave);
        book.updateAfterLending(savedLending.getCreationDate(), savedLending.getDueDate());
        member.updateAfterLending();
        reservation.updateAfterLending();
        return lendingModelAssembler.toModel(savedLending);
    }

    @Transactional
    public LendingDto renewABook(String bookBarcode) {
        Lending lending = findLendingByBookBarcode(bookBarcode);
        checkIfLendingCanBeRenewed(lending);
        lending.updateAfterRenewing();
        return lendingModelAssembler.toModel(lending);
    }

    @Transactional
    public LendingDto returnABook(String bookBarcode) {
        Lending lending = findLendingByBookBarcode(bookBarcode);
        BookItem book = lending.getBookItem();
        Member member = lending.getMember();
        BigDecimal fine = fineService.countFine(lending.getDueDate(), lending.getReturnDate());
        lending.updateAfterReturning();
        book.updateAfterReturning(lending.getReturnDate(), isBookReserved(book.getId()));
        member.updateAfterReturning(fine);
        return lendingModelAssembler.toModel(lending);
    }

    private PagedModel<LendingDto> findAllCheckouts(Pageable pageable) {
        Page<Lending> checkoutDtoPage =
                pageable.isUnpaged() ? new PageImpl<>(lendingRepository.findAll()) : lendingRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
    }

    private PagedModel<LendingDto> findLendingsByMemberId(Long memberId, Pageable pageable) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }

        List<Lending> lendingList = lendingRepository.findAll()
                .stream()
                .filter(checkout -> checkout.getMember().getId().equals(memberId))
                .toList();
        Page<Lending> checkoutDtoPage;
        if (pageable.isUnpaged()) {
            checkoutDtoPage = new PageImpl<>(lendingList);
        } else {
            checkoutDtoPage = new PageImpl<>(lendingList, pageable, lendingList.size());
        }
        return pagedResourcesAssembler.toModel(checkoutDtoPage, lendingModelAssembler);
    }

    private Lending findLendingByBookBarcode(String bookBarcode) {
        return lendingRepository.findCurrentLendingByBarcode(bookBarcode)
                .orElseThrow(() -> new LendingNotFoundException(bookBarcode));
    }

    private Reservation findReservation(Long memberId, String bookBarcode) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == ReservationStatus.READY)
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }

    private Lending findLending(Long id) {
        return lendingRepository.findById(id)
                .orElseThrow(() -> new LendingNotFoundException(id));
    }

    private boolean isBookReserved(Long bookItemId) {
        return reservationRepository.findAllPendingReservations(bookItemId).size() > 0;
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
        boolean bookIsReserved = isBookReserved(lending.getBookItem().getId());
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