package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationDate;

    // TODO: 05.12.2023 dodać dueDate...

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "book_item_id", referencedColumnName = "id")
    private BookItem bookItem;

    public void updateAfterCancelling() {
        status = ReservationStatus.CANCELED;
    }
    public void updateAfterLending() {
        status = ReservationStatus.COMPLETED;
    }
}
