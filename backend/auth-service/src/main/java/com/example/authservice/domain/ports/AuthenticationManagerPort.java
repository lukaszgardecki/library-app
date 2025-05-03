package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;

public interface AuthenticationManagerPort {

    boolean authenticate(Email username, Password password) throws Exception;

    Email getCurrentUsername();
}
