package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.auth.ports.PasswordEncoderPort;

public class InMemoryPasswordEncoderImpl implements PasswordEncoderPort{

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return rawPassword.toString().equals(encodedPassword);
//    }
}
