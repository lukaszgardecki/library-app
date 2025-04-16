package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.Password;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

@RequiredArgsConstructor
public class InMemoryAuthenticationManagerPortAdapter implements AuthenticationManagerPort {
    private final UserRepositoryPort userRepository;

    @Override
    public boolean authenticate(Email username, Password password) {
        Optional<User> userOpt = userRepository.findByEmail(username);

        if (userOpt.isPresent() && userOpt.get().getPsswrd().equals(password)) {
            if(userOpt.get().getStatus().equals(AccountStatus.ACTIVE)) return true;
            else throw new BadCredentialsException("Account is not active");
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public Email getCurrentUsername() {
        return new Email("currentUsername");
    }
}
