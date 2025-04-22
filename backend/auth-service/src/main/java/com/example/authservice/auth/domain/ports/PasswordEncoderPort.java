package com.example.authservice.auth.domain.ports;

public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
}
