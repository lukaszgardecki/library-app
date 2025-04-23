package com.example.authservice.domain.ports;

public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
}
