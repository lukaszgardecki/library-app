package com.example.libraryapp.domain.user.exceptions;

public class UnsettledFineException extends RuntimeException{
    public UnsettledFineException() {
        super("Message.MEMBER_UNSETTLED_CHARGES.getMessage()");
    }
}
