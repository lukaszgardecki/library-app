package com.example.authservice.auth.domain.ports;

import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.Password;

public interface AuthenticationManagerPort {

    boolean authenticate(Email username, Password password);

    Email getCurrentUsername();
}
