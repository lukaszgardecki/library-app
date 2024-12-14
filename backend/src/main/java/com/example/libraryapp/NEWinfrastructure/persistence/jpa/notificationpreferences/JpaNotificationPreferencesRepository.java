package com.example.libraryapp.NEWinfrastructure.persistence.jpa.notificationpreferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface JpaNotificationPreferencesRepository extends JpaRepository<NotificationPreferencesEntity, Long> {

    @Query("""
            SELECT np
            FROM NotificationPreferencesEntity np
            WHERE np.userId = :userId
            """)
    Optional<NotificationPreferencesEntity> findByUserId(Long userId);
}
