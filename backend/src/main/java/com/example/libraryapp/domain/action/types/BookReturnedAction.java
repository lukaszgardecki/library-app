package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RETURNED")
public class BookReturnedAction extends Action {
    public BookReturnedAction(Lending lending) {
        super(lending.getMember().getId());
        this.message = Message.ACTION_BOOK_RETURNED.formatted(
                lending.getBookItem().getBook().getTitle()
        );
    }
}
