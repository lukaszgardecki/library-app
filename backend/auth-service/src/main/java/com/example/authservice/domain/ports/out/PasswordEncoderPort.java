package com.example.authservice.domain.ports.out;

public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
}
