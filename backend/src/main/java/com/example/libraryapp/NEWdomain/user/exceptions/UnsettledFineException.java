package com.example.libraryapp.NEWdomain.user.exceptions;

import com.example.libraryapp.OLDmanagement.Message;

public class UnsettledFineException extends RuntimeException{
    public UnsettledFineException() {
        super("Message.MEMBER_UNSETTLED_CHARGES.getMessage()");
    }
}
