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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final SimpMessagingTemplate messagingTemplate;
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

    public List<ReservationResponse> findAllPendingReservations() {
        return reservationRepository.findAll().stream()
                .filter(res -> res.getStatus() == ReservationStatus.PENDING)
                .map(ReservationDtoMapper::map)
                .toList();
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
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(savedReservation);
        member.updateAfterReservation();
        book.updateAfterReservation();
        actionRepository.save(new RequestNewAction(savedReservationDto));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_CREATED, savedReservationDto);

        sendToWarehouse(savedReservationDto);
        return reservationModelAssembler.toModel(savedReservation);
    }

    @Transactional
    public void cancelAReservation(ActionRequest request) {
        Reservation reservation = findReservation(request.getMemberId(), request.getBookBarcode());
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        BookItem book = reservation.getBookItem();
        Member member = reservation.getMember();
        boolean isBookReserved = reservationRepository.findAllCurrentReservationsByBookItemId(book.getId())
                .stream()
                .anyMatch(res -> !Objects.equals(res.getMember().getId(), member.getId()));
        reservation.updateAfterCancelling();
        book.updateAfterReservationCancelling(isBookReserved);
        member.updateAfterReservationCancelling();
        actionRepository.save(new RequestCancelAction(savedReservationDto));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_CANCELLED, savedReservationDto);
    }

    @Transactional
    public ReservationResponse changeReservationStatusToReady(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        reservation.setStatus(ReservationStatus.READY);
        actionRepository.save(new RequestCompletedAction(savedReservationDto));
        notificationService.saveAndSendNotification(NotificationType.REQUEST_COMPLETED, savedReservationDto);
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

    private void sendToWarehouse(ReservationResponse reservation) {
        messagingTemplate.convertAndSend("/queue/warehouse", reservation);
    }

    private Reservation prepareNewReservation(Member member, BookItem book) {
        return Reservation.builder()
                .creationDate(LocalDateTime.now())
                .status(ReservationStatus.PENDING)
                .bookItem(book)
                .member(member)
                .build();
    }
}
