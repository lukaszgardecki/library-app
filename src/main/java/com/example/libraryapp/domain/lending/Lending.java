package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LendingStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_item_id", referencedColumnName = "id")
    private BookItem bookItem;

    public boolean isAfterDueDate() {
        return LocalDate.now().isAfter(dueDate);
    }

    public void updateAfterRenewing() {
        this.dueDate = LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS);
    }

    public void updateAfterReturning() {
        this.returnDate = LocalDate.now();
        this.status = LendingStatus.COMPLETED;
    }
}
