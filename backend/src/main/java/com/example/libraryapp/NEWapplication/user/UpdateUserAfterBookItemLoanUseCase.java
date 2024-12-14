package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserAfterBookItemLoanUseCase {
    private final UserRepository userRepository;

    void execute(Long userId) {
        userRepository.decrementTotalBooksRequested(userId);
        userRepository.incrementTotalBooksBorrowed(userId);
    }
}
