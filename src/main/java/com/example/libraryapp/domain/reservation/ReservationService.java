package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.config.CustomSecurityConfig;
import com.example.libraryapp.domain.exception.*;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
import com.example.libraryapp.domain.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {
    private static final int RESERVATION_EXP_TIME_IN_DAYS = 4;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<ReservationDto> findAllReservations() {
        boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

        if (requestFromAdmin) {
            return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                    .map(ReservationDtoMapper::map)
                    .toList();
        }
        throw new ReservationNotFoundException();
    }

    public List<ReservationDto> findReservationsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean requestFromOwner = userService.getCurrentLoggedInUserId() == userId;
        boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);
        if (requestFromOwner || requestFromAdmin) {
            return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                    .filter(res -> res.getUser().getId() == userId)
                    .map(ReservationDtoMapper::map)
                    .toList();
        }
        throw new ReservationNotFoundException();
    }

    public Optional<ReservationDto> findReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDtoMapper::map);
    }

    @Transactional
    public ReservationDto makeAReservation(ReservationToSaveDto reservation) {
        Book book = bookRepository.findById(reservation.getBookId())
                .orElseThrow(BookNotFoundException::new);
        userRepository.findById(reservation.getUserId())
                .orElseThrow(UserNotFoundException::new);

        boolean bookIsAvailable = book.getAvailability();
        boolean requestFromOwner = userService.getCurrentLoggedInUserId() == reservation.getUserId();
        boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

        if (bookIsAvailable && (requestFromOwner || requestFromAdmin)) {
            Reservation reservationToSave = getReservationToSave(reservation);
            Reservation savedReservation = reservationRepository.save(reservationToSave);
            book.setAvailability(Boolean.FALSE);
            return ReservationDtoMapper.map(savedReservation);
        }
        else if (!bookIsAvailable) throw new BookIsNotAvailableException();
        else throw new ReservationCannotBeCreatedException();
    }

    @Transactional
    public void removeAReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        boolean requestFromOwner = userService.getCurrentLoggedInUserId() == reservation.getUser().getId();
        boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);
        if (requestFromOwner || requestFromAdmin) {
            reservation.getBook().setAvailability(true);
            reservationRepository.deleteById(id);
        } else throw new ReservationCannotBeDeletedException();
    }

    private Reservation getReservationToSave(ReservationToSaveDto reservation) {
        User user = userRepository.findById(reservation.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(reservation.getBookId())
                .orElseThrow(BookNotFoundException::new);

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
