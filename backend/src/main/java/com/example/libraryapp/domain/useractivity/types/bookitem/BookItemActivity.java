package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.model.UserActivity;

public abstract class BookItemActivity extends UserActivity {

    public BookItemActivity(UserId userId) {
        super(userId);
    }
}
