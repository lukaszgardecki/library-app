package com.example.libraryapp.OLDdomain.exception.member;

import com.example.libraryapp.OLDmanagement.Message;

public class MemberHasNotReturnedBooksException extends RuntimeException {
    public MemberHasNotReturnedBooksException() {
        super("Message.MEMBER_NOT_RETURNED_BOOKS.getMessage()");
    }
}
