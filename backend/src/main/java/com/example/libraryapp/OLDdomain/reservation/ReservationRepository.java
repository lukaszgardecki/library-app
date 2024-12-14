package com.example.libraryapp.OLDdomain.reservation;

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
                    and (status = 'PENDING' or status = 'READY')
                    """,
            nativeQuery = true
    )
    List<Reservation> findAllCurrentReservationsByBookItemId(@Param("bookItemId") Long bookItemId);

    @Query(
            value = """
                    select * from Reservation
                    where book_item_id = :bookItemId
                    and status = 'PENDING'
                    """,
            nativeQuery = true
    )
    List<Reservation> findAllPendingReservationByBookItemId(@Param("bookItemId") Long bookItemId);

    @Query(
            value = """
                    select * from Reservation
                    where member_id = :memberId
                    and (status = 'PENDING' or status = 'READY')
                    """,
            nativeQuery = true
    )
    List<Reservation> findAllCurrentReservationsByMemberId(Long memberId);
}
