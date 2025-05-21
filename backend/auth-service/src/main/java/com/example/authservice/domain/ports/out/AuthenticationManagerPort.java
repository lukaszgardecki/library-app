package com.example.authservice.domain.ports.out;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;

public interface AuthenticationManagerPort {

    boolean authenticate(Email username, Password password) throws Exception;

    Email getCurrentUsername();
}
