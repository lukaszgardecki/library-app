package com.example.libraryapp.NEWdomain.auth.ports;

public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
}
