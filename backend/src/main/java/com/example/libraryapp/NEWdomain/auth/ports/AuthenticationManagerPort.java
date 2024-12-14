package com.example.libraryapp.NEWdomain.auth.ports;

public interface AuthenticationManagerPort {

    boolean authenticate(String username, String password);

    String getCurrentUsername();
}
