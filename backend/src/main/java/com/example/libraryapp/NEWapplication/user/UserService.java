package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepository;

    User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
