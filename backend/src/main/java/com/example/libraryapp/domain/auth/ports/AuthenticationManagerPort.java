package com.example.libraryapp.domain.auth.ports;

import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.Password;

public interface AuthenticationManagerPort {

    boolean authenticate(Email username, Password password);

    Email getCurrentUsername();
}
