package com.example.libraryapp.NEWinfrastructure.spring;

import com.example.libraryapp.NEWdomain.auth.ports.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PasswordEncoderImpl implements PasswordEncoderPort {
    private final PasswordEncoder passwordEncoder;


    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


}
