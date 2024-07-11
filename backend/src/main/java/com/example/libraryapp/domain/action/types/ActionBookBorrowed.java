package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_BORROWED")
public class ActionBookBorrowed extends Action {
    public ActionBookBorrowed(LendingDto lending) {
        super(lending.getMember().getId());
        if (lending.getBookItem().getIsReferenceOnly()) {
            this.message = Message.ACTION_BOOK_BORROWED_ON_SITE.getMessage(
                    lending.getBookItem().getBook().getTitle()
            );
        } else {
            this.message = Message.ACTION_BOOK_BORROWED.getMessage(
                    lending.getBookItem().getBook().getTitle()
            );
        }

    }
}
