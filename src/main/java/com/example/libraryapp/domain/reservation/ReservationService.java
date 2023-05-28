package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.BookRepository;
import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import com.example.libraryapp.domain.exception.ReservationNotFoundException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
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

    public ReservationService(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<ReservationDto> findAllReservations() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .map(ReservationDtoMapper::map)
                .toList();
    }

    public List<ReservationDto> findReservationsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .filter(res -> res.getUser().getId() == userId)
                .map(ReservationDtoMapper::map)
                .toList();
    }

    public Optional<ReservationDto> findReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDtoMapper::map);
    }

    @Transactional
    public ReservationDto makeAReservation(ReservationToSaveDto reservation) {
        Book book = bookRepository.findById(reservation.getBookId())
                .orElseThrow(BookNotFoundException::new);
        boolean bookIsAvailable = book.getAvailability();

        if (bookIsAvailable) {
            Reservation reservationToSave = getReservationToSave(reservation);
            Reservation savedReservation = reservationRepository.save(reservationToSave);
            book.setAvailability(Boolean.FALSE);
            return ReservationDtoMapper.map(savedReservation);
        } else {
            throw new BookIsNotAvailableException();
        }
    }

    @Transactional
    public void removeAReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException();
        }
        reservationRepository.deleteById(id);
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
