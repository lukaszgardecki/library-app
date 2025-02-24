package com.example.libraryapp.domain.auth.ports;

public interface AuthenticationManagerPort {

    boolean authenticate(String username, String password);

    String getCurrentUsername();
}
