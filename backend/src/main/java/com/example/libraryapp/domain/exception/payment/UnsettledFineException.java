package com.example.libraryapp.domain.exception.payment;

import com.example.libraryapp.management.Message;

public class UnsettledFineException extends RuntimeException{
    public UnsettledFineException() {
        super(Message.MEMBER_UNSETTLED_CHARGES.getMessage());
    }
}
