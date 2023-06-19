package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.config.assembler.ReservationModelAssembler;
import com.example.libraryapp.domain.exception.*;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
import com.example.libraryapp.domain.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private static final int RESERVATION_EXP_TIME_IN_DAYS = 4;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ReservationModelAssembler reservationModelAssembler;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    public ReservationService(ReservationRepository reservationRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository,
                              UserService userService,
                              ReservationModelAssembler reservationModelAssembler,
                              PagedResourcesAssembler<Reservation> pagedResourcesAssembler) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.reservationModelAssembler = reservationModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationDtoMapper::map)
                .toList();
    }

    public PagedModel<ReservationDto> findAllReservations(Pageable pageable) {
        boolean currentLoggedInUserIsAdmin = userService.checkIfCurrentLoggedInUserIsAdmin();
        if (currentLoggedInUserIsAdmin) {

            Page<Reservation> reservationDtoPage =
                    pageable.isUnpaged() ? new PageImpl<>(reservationRepository.findAll()) : reservationRepository.findAll(pageable);
            return pagedResourcesAssembler.toModel(reservationDtoPage, reservationModelAssembler);
        }
        throw new ReservationNotFoundException();
    }

    public PagedModel<ReservationDto> findReservationsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(userId);

        if (userIsAdminOrDataOwner) {
            List<Reservation> reservationList = reservationRepository.findAll()
                    .stream()
                    .filter(res -> res.getUser().getId().equals(userId))
                    .toList();
            Page<Reservation> reservationDtoPage;
            if (pageable.isUnpaged()) {
                reservationDtoPage = new PageImpl<>(reservationList);
            } else {
                reservationDtoPage = new PageImpl<>(reservationList, pageable, reservationList.size());
            }
            return pagedResourcesAssembler.toModel(reservationDtoPage, reservationModelAssembler);
        }
        throw new ReservationNotFoundException();
    }

    public Optional<ReservationDto> findReservationById(Long id) {
        return reservationRepository.findById(id)
                .filter(res -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(res.getUser().getId()))
                .map(reservationModelAssembler::toModel);
    }

    @Transactional
    public ReservationDto makeAReservation(ReservationToSaveDto reservation) {
        User user = userRepository.findById(reservation.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(reservation.getBookId())
                .orElseThrow(BookNotFoundException::new);

        boolean bookIsAvailable = book.getAvailability();
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(reservation.getUserId());

        if (bookIsAvailable && userIsAdminOrDataOwner) {
            Reservation reservationToSave = prepareReservationToSave(user, book);
            Reservation savedReservation = reservationRepository.save(reservationToSave);
            book.setAvailability(Boolean.FALSE);
            return reservationModelAssembler.toModel(savedReservation);
        }
        else if (!bookIsAvailable) throw new BookIsNotAvailableException();
        else throw new ReservationCannotBeCreatedException();
    }

    @Transactional
    public void removeAReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(reservation.getUser().getId());
        if (userIsAdminOrDataOwner) {
            reservation.getBook().setAvailability(true);
            reservationRepository.deleteById(id);
        } else throw new ReservationCannotBeDeletedException();
    }

    private Reservation prepareReservationToSave(User user, Book book) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(RESERVATION_EXP_TIME_IN_DAYS);

        Reservation reservationToSave = new Reservation();
        reservationToSave.setUser(user);
        reservationToSave.setBook(book);
        reservationToSave.setStartTime(startTime);
        reservationToSave.setEndTime(endTime);
        return  reservationToSave;
    }
}
