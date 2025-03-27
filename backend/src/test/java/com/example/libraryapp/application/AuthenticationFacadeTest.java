package com.example.libraryapp.application;

import com.example.libraryapp.application.auth.AuthenticationConfiguration;
import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationFacadeTest {
    private AuthenticationFacade authenticationFacade;
    private InMemoryUserRepositoryAdapter userRepository;
    private UserFacade userFacade;
    private final TestHelper testHelper = new TestHelper();


    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepositoryAdapter();
        userFacade = new UserConfiguration().userFacade(userRepository);
        authenticationFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
    }


    @Test
    @DisplayName("Should authenticate a user if credentials are correct.")
    void shouldAuthenticateUser() {

        // Given
        String username = "testUser";
        String password = "testPass";
        User user = testHelper.getUser();
        user.setPsswrd(password);
        user.setEmail(username);
        userRepository.putUser(user);

        // When
        AuthTokensDto auth = authenticationFacade.authenticate(username, password);

        // Then
        assertNotNull(auth);
        assertNotNull(auth.accessToken());
        assertNotNull(auth.refreshToken());
    }

    @Test
    @DisplayName("Should not authenticate a user if password is incorrect.")
    void shouldNotAuthenticateUser() {

        // Given
        String username = "testUser";
        String password = "testPass";
        User user = testHelper.getUser();
        user.setPsswrd(password);
        user.setEmail(username);
        userRepository.putUser(user);

        // When
        Executable authenticate = () -> authenticationFacade.authenticate(username, "wrongPassword");

        // Then
        assertThrows(BadCredentialsException.class, authenticate);
    }

    @Test
    @DisplayName("Should not authenticate a user if username is incorrect.")
    void shouldNotAuthenticateUserIfUsernameIsIncorrect() {
        // Given
        String username = "testUser";
        String password = "testPass";
        User user = testHelper.getUser();
        user.setPsswrd(password);
        user.setEmail(username);
        userRepository.putUser(user);
        // When
        Executable authenticate = () -> authenticationFacade.authenticate("wrongUsername", password);
        // Then
        assertThrows(BadCredentialsException.class, authenticate);
    }

    @ParameterizedTest
    @EnumSource(AccountStatus.class)
    @DisplayName("Should authenticate a user if their account is not ACTIVE.")
    void shouldNotAuthenticateUserIfAccountIsSuspended(AccountStatus accountStatus) {
        // Given
        String username = "testUser";
        String password = "testPass";
        User user = testHelper.getUser();
        user.setPsswrd(password);
        user.setEmail(username);
        user.setStatus(accountStatus);
        userRepository.putUser(user);

        // When
        Executable authenticate = () -> authenticationFacade.authenticate(username, password);

        // Then
        if (List.of(
                AccountStatus.INACTIVE, AccountStatus.SUSPENDED,
                AccountStatus.CLOSED, AccountStatus.PENDING
            ).contains(accountStatus)
        ) {
            assertThrows(BadCredentialsException.class, authenticate);
        }
    }
}
