package com.example.libraryapp.domain.exception.card;

import com.example.libraryapp.management.Message;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(Long id) {
        super(Message.CARD_NOT_FOUND.getMessage(id));
    }
}
