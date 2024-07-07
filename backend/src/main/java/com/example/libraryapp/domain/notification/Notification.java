package com.example.libraryapp.domain.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected LocalDateTime createdAt;
    protected String subject;
    protected String content;
    @Formula("type")
    protected String type;
    protected Boolean read;

    protected Long bookId;
    protected String bookTitle;

    protected Long memberId;

    protected Notification(Long memberId) {
        this.memberId = memberId;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }
}
