package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.Password;

public interface AuthenticationManagerPort {

    boolean authenticate(Email username, Password password);

    Email getCurrentUsername();
}
