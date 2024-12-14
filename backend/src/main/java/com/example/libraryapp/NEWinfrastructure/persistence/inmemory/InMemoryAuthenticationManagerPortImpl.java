package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.NEWdomain.user.model.AccountStatus;
import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

@RequiredArgsConstructor
public class InMemoryAuthenticationManagerPortImpl implements AuthenticationManagerPort {
    private final UserRepository userRepository;

    @Override
    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByEmail(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            if(userOpt.get().getStatus().equals(AccountStatus.ACTIVE)) return true;
            else throw new BadCredentialsException("Account is not active");
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public String getCurrentUsername() {
        return "currentUsername";
    }
}
