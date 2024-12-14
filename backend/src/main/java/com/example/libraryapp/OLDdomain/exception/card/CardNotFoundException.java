package com.example.libraryapp.OLDdomain.exception.card;

import com.example.libraryapp.OLDmanagement.Message;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(Long id) {
        super("Message.CARD_NOT_FOUND.getMessage(id)");
    }
}
