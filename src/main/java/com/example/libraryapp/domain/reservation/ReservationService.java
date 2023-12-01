package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemRepository;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.config.assembler.ReservationModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookLimitException;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ResevationException;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookItemRepository bookItemRepository;
    private final MemberRepository memberRepository;
    private final ReservationModelAssembler reservationModelAssembler;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    public ReservationService(ReservationRepository reservationRepository,
                              BookItemRepository bookItemRepository,
                              MemberRepository memberRepository,
                              ReservationModelAssembler reservationModelAssembler,
                              PagedResourcesAssembler<Reservation> pagedResourcesAssembler) {
        this.reservationRepository = reservationRepository;
        this.bookItemRepository = bookItemRepository;
        this.memberRepository = memberRepository;
        this.reservationModelAssembler = reservationModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<ReservationResponse> findAllReservations(Pageable pageable) {
        Page<Reservation> reservationPage =
                pageable.isUnpaged() ? new PageImpl<>(reservationRepository.findAll()) : reservationRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(reservationPage, reservationModelAssembler);
    }

    public PagedModel<ReservationResponse> findReservationsByUserId(Long memberId, Pageable pageable) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        List<Reservation> reservationList = reservationRepository.findAllByMemberId(memberId);
        Page<Reservation> reservationPage;
        if (pageable.isUnpaged()) {
            reservationPage = new PageImpl<>(reservationList);
        } else {
            reservationPage = new PageImpl<>(reservationList, pageable, reservationList.size());
        }
        return pagedResourcesAssembler.toModel(reservationPage, reservationModelAssembler);
    }

    public Optional<ReservationResponse> findReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationModelAssembler::toModel);
    }

    @Transactional
    public ReservationResponse makeAReservation(Long memberId, String bookBarcode) {
        BookItem book = findBookByBarcode(bookBarcode);
        Member member = findMemberById(memberId);
        checkMemberReservationLimit(member);
        checkIfBookIsNotLost(book);
        checkIfReservationAlreadyExist(memberId, bookBarcode);
        Reservation reservationToSave = prepareNewReservation(member, book);
        Reservation savedReservation = reservationRepository.save(reservationToSave);
        member.updateAfterReservation();
        book.updateAfterReservation();
        return reservationModelAssembler.toModel(savedReservation);
    }

    @Transactional
    public void cancelAReservation(Long memberId, String bookBarcode) {
        Reservation reservation = findReservation(memberId, bookBarcode);
        BookItem book = reservation.getBookItem();
        Member member = reservation.getMember();
        boolean isBookReserved = isBookReserved(book.getId());
        reservation.updateAfterCancelling();
        book.updateAfterReservationCancelling(isBookReserved);
        member.updateAfterReservationCancelling();
    }

    private Reservation findReservation(Long memberId, String bookBarcode) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == ReservationStatus.PENDING || res.getStatus() == ReservationStatus.READY)
                .findAny()
                .orElseThrow(ReservationNotFoundException::new);
    }

    private BookItem findBookByBarcode(String bookBarcode) {
        return bookItemRepository.findByBarcode(bookBarcode)
                .orElseThrow(BookNotFoundException::new);
    }
    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private void checkIfReservationAlreadyExist(Long memberId, String bookBarcode) {
        Optional<Reservation> reservation = reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> Objects.equals(res.getMember().getId(), memberId))
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == ReservationStatus.READY || res.getStatus() == ReservationStatus.PENDING)
                .findAny();
        if (reservation.isPresent()) throw new ResevationException("The reservation has already been created.");
    }

    private void checkMemberReservationLimit(Member member) {
        if (member.getTotalBooksReserved() >= Constants.MAX_BOOKS_RESERVED_BY_USER) {
            throw new BookLimitException("The user has already reserved maximum number of books");
        }
    }

    private void checkIfBookIsNotLost(BookItem book) {
        if (book.getStatus() == BookItemStatus.LOST) throw new ResevationException("Book cannot be reserved");
    }

    private boolean isBookReserved(Long bookItemId) {
        return reservationRepository.findAllPendingReservations(bookItemId).size() > 0;
    }

    private Reservation prepareNewReservation(Member member, BookItem book) {
        return Reservation.builder()
                .creationDate(LocalDate.now())
                .status(ReservationStatus.PENDING)
                .bookItem(book)
                .member(member)
                .build();
    }
}
