package com.example.libraryapp.domain.auth.ports;

public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
}
