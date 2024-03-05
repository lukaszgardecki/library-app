package com.example.libraryapp.domain.exception.card;

import com.example.libraryapp.management.Message;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(Long id) {
        super(String.format(Message.CARD_NOT_FOUND,id));
    }
}
