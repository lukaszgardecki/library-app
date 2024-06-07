package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RENEWED")
public class BookRenewedAction extends Action {
    public BookRenewedAction(Lending lending) {
        super(lending.getMember().getId());
        this.message = Message.ACTION_BOOK_RENEWED.formatted(
                lending.getBookItem().getBook().getTitle()
        );
    }
}
