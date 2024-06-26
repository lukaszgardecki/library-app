package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.action.ActionRepository;
import com.example.libraryapp.domain.action.types.RequestCancelAction;
import com.example.libraryapp.domain.action.types.RequestCompletedAction;
import com.example.libraryapp.domain.action.types.RequestNewAction;
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
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.notification.NotificationType;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookItemRepository bookItemRepository;
    private final MemberRepository memberRepository;
    private final ActionRepository actionRepository;
    private final NotificationService notificationService;
    private final ReservationModelAssembler reservationModelAssembler;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    public PagedModel<ReservationResponse> findReservations(Long memberId, ReservationStatus status, Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(res -> memberId == null || Objects.equals(res.getMember().getId(), memberId))
                .filter(res -> status == null || res.getStatus() == status)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reservations.size());
        List<Reservation> paginatedList = reservations.subList(start, end);
        Page<Reservation> reservationPage = new PageImpl<>(paginatedList, pageable, reservations.size());
        return pagedResourcesAssembler.toModel(reservationPage, reservationModelAssembler);
    }

    public PagedModel<ReservationResponse> findAllPendingReservations(Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll(pageable).stream()
                .filter(res -> res.getStatus() == ReservationStatus.PENDING)
                .toList();
        return pagedResourcesAssembler.toModel(new PageImpl<>(reservations), reservationModelAssembler);
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
        actionRepository.save(new RequestNewAction(savedReservation));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_CREATED, ReservationDtoMapper.map(savedReservation));
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
        actionRepository.save(new RequestCancelAction(reservation));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_CANCELLED, ReservationDtoMapper.map(reservation));
    }

    @Transactional
    public ReservationResponse changeReservationStatusToReady(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        reservation.setStatus(ReservationStatus.READY);
        actionRepository.save(new RequestCompletedAction(reservation));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_COMPLETED, ReservationDtoMapper.map(reservation));
        return reservationModelAssembler.toModel(reservation);
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
}
