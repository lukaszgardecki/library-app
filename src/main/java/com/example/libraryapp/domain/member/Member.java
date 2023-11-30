package com.example.libraryapp.domain.member;

import com.example.libraryapp.domain.card.LibraryCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Member extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "library_card_id")
    private LibraryCard card;

    public void incrementTotalBooksBorrowed() {
        totalBooksBorrowed++;
    }

    public void decrementTotalBooksBorrowed() {
        totalBooksBorrowed--;
    }

    public void incrementTotalBooksReserved() {
        totalBooksReserved++;
    }

    public void decrementTotalBooksReserved() {
        totalBooksReserved--;
    }

    public boolean hasCharges() {
        return charge.compareTo(BigDecimal.ZERO) > 0;
    }

    public void addCharge(BigDecimal fine) {
        charge = charge.add(fine);
    }

    public void updateAfterReservation() {
        incrementTotalBooksReserved();
    }

    public void updateAfterReservationCancelling() {
        decrementTotalBooksReserved();
    }

    public void updateAfterLending() {
        incrementTotalBooksBorrowed();
        decrementTotalBooksReserved();
    }

    public void updateAfterReturning(BigDecimal fine) {
        addCharge(fine);
        decrementTotalBooksBorrowed();
    }

//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private List<Lending> lendings;
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private List<Reservation> reservations;

//    public void cancelAllReservations() {
//        reservations.forEach(res -> res.getBook().setAvailability(true));
//    }
}