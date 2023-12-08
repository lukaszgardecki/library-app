package com.example.libraryapp.domain.exception.fine;

import com.example.libraryapp.management.Message;

public class UnsettledFineException extends RuntimeException{
    public UnsettledFineException() {
        super(Message.UNSETTLED_CHARGES);
    }
}
