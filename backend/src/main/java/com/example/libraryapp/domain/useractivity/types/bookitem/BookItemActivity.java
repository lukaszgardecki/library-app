package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivity;

public abstract class BookItemActivity extends UserActivity {

    public BookItemActivity(Long userId) {
        super(userId);
    }
}
