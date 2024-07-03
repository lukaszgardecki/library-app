package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RESERVED_FIRST")
public class ActionBookReservedFirstPerson extends Action {
    public ActionBookReservedFirstPerson(Long userId, LendingDto lending) {
        super(userId);
        this.message = Message.ACTION_BOOK_RESERVED_FIRST_PERSON.formatted(
                lending.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                lending.getBookItem().getBook().getTitle()
        );
    }
}
