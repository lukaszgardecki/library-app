package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_LOST")
public class ActionBookLost extends Action {
    public ActionBookLost(LendingDto lending) {
        super(lending.getMember().getId());
        this.message = Message.ACTION_BOOK_LOST.getMessage(
                lending.getBookItem().getBook().getTitle()
        );
    }
}
