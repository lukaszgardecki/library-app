package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REQUEST_COMPLETED")
public class RequestCompletedAction extends Action {
    public RequestCompletedAction(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.message = Message.ACTION_REQUEST_COMPLETED.formatted(
                reservation.getBookItem().getBook().getTitle()
        );
    }
}
