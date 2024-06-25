package com.example.libraryapp.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
                    SELECT * FROM notification
                    WHERE (:memberId IS NULL OR member_id = :memberId) 
                    """,
           countQuery = """
                    SELECT count(*) FROM notification
                    WHERE (:memberId IS NULL OR member_id = :memberId) 
                    """,
           nativeQuery = true
    )
    Page<Notification> findAllByParams(@Param("memberId") Long memberId, Pageable pageable);
}
