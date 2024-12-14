package com.example.libraryapp.OLDdomain.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = """
                    SELECT * FROM payment
                    WHERE (:memberId IS NULL OR member_id = :memberId) 
                    """,
            countQuery = """
                    SELECT count(*) FROM payment
                    WHERE (:memberId IS NULL OR member_id = :memberId) 
                    """,
            nativeQuery = true
    )
    Page<Payment> findAllByParams(@Param("memberId") Long memberId, Pageable pageable);
}
