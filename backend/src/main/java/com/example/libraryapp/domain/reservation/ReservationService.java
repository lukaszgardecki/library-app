package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.action.ActionService;
import com.example.libraryapp.domain.action.types.*;
import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.BookItemRepository;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.config.assembler.ReservationModelAssembler;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.lending.LendingDtoMapper;
import com.example.libraryapp.domain.lending.LendingRepository;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.notification.types.*;
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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final LendingRepository lendingRepository;
    private final BookItemRepository bookItemRepository;
    private final MemberRepository memberRepository;
    private final ActionService actionService;
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

    public List<ReservationResponse> findAllPendingReservationsByBookItemId(Long bookItemId) {
        return reservationRepository.findAllPendingReservationByBookItemId(bookItemId).stream()
                .map(ReservationDtoMapper::map)
                .toList();
    }

    public ReservationResponse findReservationById(Long id) {
        Reservation reservation = findReservation(id);
        return reservationModelAssembler.toModel(reservation);
    }

    public Reservation findReservation(Long memberId, String bookBarcode, ReservationStatus status) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .filter(res -> res.getBookItem().getBarcode().equals(bookBarcode))
                .filter(res -> res.getStatus() == status)
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
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
        member.addReservedItemId(book.getId());

        switch (book.getStatus()) {
            case AVAILABLE -> handleAvailableBook(savedReservation);
            case LOANED, RESERVED -> handleLoanedOrReservedBook(savedReservation);
        }
        return reservationModelAssembler.toModel(savedReservation);
    }

    @Transactional
    public void cancelAReservation(ActionRequest request) {
        Reservation reservation = findReservation(request.getMemberId(), request.getBookBarcode(), ReservationStatus.PENDING);
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        BookItem book = reservation.getBookItem();
        Member member = reservation.getMember();
        boolean isBookReserved = reservationRepository.findAllCurrentReservationsByBookItemId(book.getId())
                .stream()
                .anyMatch(res -> !Objects.equals(res.getMember().getId(), member.getId()));
        reservation.updateAfterCancelling();
        book.updateAfterReservationCancelling(isBookReserved);
        member.updateAfterReservationCancelling();
        member.removeReservedItemId(book.getId());
        actionService.save(new ActionRequestCancel(savedReservationDto));
        notificationService.sendToUser(new NotificationRequestCancelled(savedReservationDto), savedReservationDto.getMember());
    }

    @Transactional
    public void cancelReservations(Long bookId) {
        reservationRepository.findAllCurrentReservationsByBookItemId(bookId)
                .forEach(res -> {
                    ReservationResponse savedReservationDto = ReservationDtoMapper.map(res);
                    int numOfRes = res.getMember().getTotalBooksReserved();
                    res.setStatus(ReservationStatus.CANCELED);
                    res.getMember().setTotalBooksReserved(numOfRes - 1);
                    res.getMember().removeReservedItemId(res.getBookItem().getId());

                    notificationService.sendToUser(new NotificationReservationCancelBookItemLost(savedReservationDto), savedReservationDto.getMember());
                });
    }

    @Transactional
    public ReservationResponse changeReservationStatusToReady(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        reservation.setStatus(ReservationStatus.READY);
        actionService.save(new ActionRequestCompleted(savedReservationDto));
        notificationService.sendToUser(new NotificationRequestCompleted(savedReservationDto), savedReservationDto.getMember());
        return reservationModelAssembler.toModel(reservation);
    }

    public boolean isBookReserved(Long bookItemId) {
        return reservationRepository.findAllCurrentReservationsByBookItemId(bookItemId).size() > 0;
    }

    private void handleAvailableBook(Reservation reservation) {
        reservation.getBookItem().setStatus(BookItemStatus.RESERVED);
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        actionService.save(new ActionRequestCreated(savedReservationDto));
        notificationService.sendToUser(new NotificationRequestCreated(savedReservationDto), savedReservationDto.getMember());
        notificationService.sendToWarehouse(savedReservationDto);
    }

    private void handleLoanedOrReservedBook(Reservation reservation) {
        Long bookId = reservation.getBookItem().getId();
        Long memberId = reservation.getMember().getId();
        List<Reservation> otherCurrentReservations = reservationRepository.findAllPendingReservationByBookItemId(bookId).stream()
                .filter(res -> !Objects.equals(res.getId(), reservation.getId()))
                .toList();
        ReservationResponse savedReservationDto = ReservationDtoMapper.map(reservation);
        int queuePosition = getQueuePosition(otherCurrentReservations, reservation);
        Optional<LendingDto> currentLending = lendingRepository.findCurrentLendingByBookItemId(bookId)
                .map(LendingDtoMapper::map);

        if (otherCurrentReservations.isEmpty() && currentLending.isPresent()) {
            notificationService.sendToUser(new NotificationRenewalImpossible(currentLending.get()), savedReservationDto.getMember());
            actionService.save(new ActionBookReservedFirstPerson(memberId, currentLending.get()));
            notificationService.sendToUser(new NotificationBookReservedFirstPerson(memberId, currentLending.get()), savedReservationDto.getMember());
        } else if (currentLending.isPresent()) {
            actionService.save(new ActionBookReservedQueue(memberId, reservation.getBookItem().getBook().getTitle(), queuePosition));
            notificationService.sendToUser(new NotificationBookReservedQueue(savedReservationDto, queuePosition), savedReservationDto.getMember());
        }
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

    private int getQueuePosition(List<Reservation> reservations, Reservation savedReservation) {
        return reservations.stream()
                .sorted(Comparator.comparing(Reservation::getCreationDate))
                .toList()
                .indexOf(savedReservation) + 1;
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
