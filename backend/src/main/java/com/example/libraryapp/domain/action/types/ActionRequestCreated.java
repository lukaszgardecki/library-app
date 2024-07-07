package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REQUEST_NEW")
public class ActionRequestCreated extends Action {
    public ActionRequestCreated(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.message = Message.ACTION_REQUEST_SENT.formatted(
                reservation.getBookItem().getBook().getTitle()
        );
    }
}
