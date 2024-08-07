package com.example.libraryapp.domain.exception.member;

import com.example.libraryapp.management.Message;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
    }

    public MemberNotFoundException(Long memberId) {
        super(Message.MEMBER_NOT_FOUND.getMessage(memberId));
    }
}
