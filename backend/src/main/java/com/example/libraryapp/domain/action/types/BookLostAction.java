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
public class BookLostAction extends Action {
    public BookLostAction(LendingDto lending) {
        super(lending.getMember().getId());
        this.message = Message.ACTION_BOOK_LOST.formatted(
                lending.getBookItem().getBook().getTitle()
        );
    }
}
