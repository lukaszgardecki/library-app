package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemRepository;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.config.assembler.ReservationModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.notification.NotificationDetails;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Constants;
import com.example.libraryapp.management.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookItemRepository bookItemRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final ReservationModelAssembler reservationModelAssembler;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    public PagedModel<ReservationResponse> findReservations(Long memberId, Pageable pageable) {
        PagedModel<ReservationResponse> collectionModel;
        if (memberId != null) {
            collectionModel = findReservationsByUserId(memberId, pageable);
        } else {
            collectionModel = findAllReservations(pageable);
        }
        return collectionModel;
    }

    public ReservationResponse findReservationById(Long id) {
        Reservation reservation = findReservation(id);
        return reservationModelAssembler.toModel(reservation);
    }

    @Transactional
    public ReservationResponse makeAReservation(ActionRequest request) {
        BookItem book = findBookByBarcode(request.getBookBarcode());
        Member member = findMemberById(request.getMemberId());
        checkMemberReservationLimit(member);
        checkIfReservationAlreadyExist(member.getId(), book.getBarcode());
        checkIfBookItemIsNotLost(book);
        Reservation reservationToSave = prepareNewReservation(member, book);
        Reservation savedReservation = reservationRepository.save(reservationToSave);
        member.updateAfterReservation();
        book.updateAfterReservation();
        NotificationDetails details = createNotificationDetails(
                member, Message.RESERVATION_CREATED, book.getBook().getTitle()
        );
        notificationService.sendNotification(details);
        return reservationModelAssembler.toModel(savedReservation);
    }

    @Transactional
    public void cancelAReservation(ActionRequest request) {
        Reservation reservation = findReservation(request.getMemberId(), request.getBookBarcode());
        BookItem book = reservation.getBookItem();
        Member member = reservation.getMember();
        boolean isBookReserved = reservationRepository.findAllCurrentReservationsByBookItemId(book.getId())
                .stream()
                .anyMatch(res -> !Objects.equals(res.getMember().getId(), member.getId()));
        reservation.updateAfterCancelling();
        book.updateAfterReservationCancelling(isBookReserved);
        member.updateAfterReservationCancelling();
        NotificationDetails details = createNotificationDetails(
                member, Message.RESERVATION_DELETED, book.getBook().getTitle()
        );
        notificationService.sendNotification(details);
    }

    private PagedModel<ReservationResponse> findAllReservations(Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(reservationPage, reservationModelAssembler);
    }

    private PagedModel<ReservationResponse> findReservationsByUserId(Long memberId, Pageable pageable) {
        Member member = findMemberById(memberId);
        List<Reservation> reservationList = reservationRepository.findAllByMemberId(member.getId());
        Page<Reservation> reservationPage;
        if (pageable.isUnpaged()) {
            reservationPage = new PageImpl<>(reservationList);
        } else {
            reservationPage = new PageImpl<>(reservationList, pageable, reservationList.size());
        }
        return pagedResourcesAssembler.toModel(reservationPage, reservationModelAssembler);
    }

    private BookItem findBookByBarcode(String bookBarcode) {
        return bookItemRepository.findByBarcode(bookBarcode)
                .orElseThrow(() -> new BookItemNotFoundException(bookBarcode));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Reservation findReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    private Reservation findReservation(Long memberId, String bookBarcode) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == ReservationStatus.PENDING || res.getStatus() == ReservationStatus.READY)
                .findAny()
                .orElseThrow(ReservationNotFoundException::new);
    }

    private void checkIfReservationAlreadyExist(Long memberId, String bookBarcode) {
        Optional<Reservation> reservation = reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> Objects.equals(res.getMember().getId(), memberId))
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == ReservationStatus.READY || res.getStatus() == ReservationStatus.PENDING)
                .findAny();
        if (reservation.isPresent()) throw new ReservationException(Message.RESERVATION_ALREADY_CREATED);
    }

    private void checkMemberReservationLimit(Member member) {
        if (member.getTotalBooksReserved() >= Constants.MAX_BOOKS_RESERVED_BY_USER) {
            throw new ReservationException(Message.RESERVATION_LIMIT_EXCEEDED);
        }
    }

    private void checkIfBookItemIsNotLost(BookItem book) {
        if (book.getStatus() == BookItemStatus.LOST) {
            throw new ReservationException(Message.RESERVATION_BOOK_ITEM_LOST);
        }
    }

    private Reservation prepareNewReservation(Member member, BookItem book) {
        return Reservation.builder()
                .creationDate(LocalDate.now())
                .status(ReservationStatus.PENDING)
                .bookItem(book)
                .member(member)
                .build();
    }

    private NotificationDetails createNotificationDetails(Member member, String notificationMessage, String bookTitle) {
        return NotificationDetails.builder()
                .createdAt(LocalDateTime.now())
                .content(notificationMessage)
                .bookTitle(bookTitle)
                .userEmail(member.getEmail())
                .userPhoneNumber(member.getPerson().getPhone())
                .build();
    }
}
