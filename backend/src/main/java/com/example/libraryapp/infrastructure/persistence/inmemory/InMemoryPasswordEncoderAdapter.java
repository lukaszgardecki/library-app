package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.auth.ports.PasswordEncoderPort;

public class InMemoryPasswordEncoderAdapter implements PasswordEncoderPort{

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return rawPassword.toString().equals(encodedPassword);
//    }
}
