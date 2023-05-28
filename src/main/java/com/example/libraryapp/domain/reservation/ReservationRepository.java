package com.example.libraryapp.domain.reservation;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Optional<Reservation> findReservationByBook_IdAndUser_Id(Long bookId, Long userId);
}
