package com.example.libraryapp.infrastructure.persistence.jpa.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query(value = """
        SELECT n
        FROM NotificationEntity n
        WHERE (:userId IS NULL OR n.userId = :userId)
    """,
    countQuery = """
        SELECT count(n)
        FROM NotificationEntity n
        WHERE (:userId IS NULL OR n.userId = :userId)
    """)
    Page<NotificationEntity> findAllByParams(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE NotificationEntity n
        SET n.isRead = true
        WHERE n.id = :id
    """)
    void markAsRead(Long id);
}
