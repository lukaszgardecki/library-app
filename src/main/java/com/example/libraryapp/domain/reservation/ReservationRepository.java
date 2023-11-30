package com.example.libraryapp.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(Long memberId);

    @Query(
            value = """
            select * from Reservation
            where book_item_id = :bookItemId
            and status = 'PENDING'
            """,
            nativeQuery = true
    )
    List<Reservation> findAllPendingReservations(@Param("bookItemId") Long bookItemId);
}
