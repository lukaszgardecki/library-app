package com.example.paymentservice.infrastructure.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query(value = """
        SELECT p
        FROM PaymentEntity p
        WHERE (:userId IS NULL OR p.userId = :userId)
    """,
    countQuery = """
        SELECT count(p)
        FROM PaymentEntity p
        WHERE (:userId IS NULL OR p.userId = :userId)
    """)
    Page<PaymentEntity> findAllByParams(@Param("userId") Long userId, Pageable pageable);
}
