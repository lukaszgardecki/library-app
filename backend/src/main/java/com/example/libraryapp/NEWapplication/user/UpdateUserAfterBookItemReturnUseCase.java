package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserAfterBookItemReturnUseCase {
    private final UserRepository userRepository;

    void execute(Long userId) {
        userRepository.decrementTotalBooksBorrowed(userId);
    }
}
