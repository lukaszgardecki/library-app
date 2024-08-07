package com.example.libraryapp.domain.exception.member;

import com.example.libraryapp.management.Message;

public class MemberHasNotReturnedBooksException extends RuntimeException {
    public MemberHasNotReturnedBooksException() {
        super(Message.MEMBER_NOT_RETURNED_BOOKS.getMessage());
    }
}
