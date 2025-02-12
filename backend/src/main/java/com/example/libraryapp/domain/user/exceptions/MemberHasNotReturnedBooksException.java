package com.example.libraryapp.domain.user.exceptions;

public class MemberHasNotReturnedBooksException extends RuntimeException {
    public MemberHasNotReturnedBooksException() {
        super("Message.MEMBER_NOT_RETURNED_BOOKS.getMessage()");
    }
}
