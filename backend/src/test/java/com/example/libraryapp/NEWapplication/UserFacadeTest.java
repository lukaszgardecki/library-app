package com.example.libraryapp.NEWapplication;

import com.example.libraryapp.NEWapplication.user.UserConfiguration;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.user.dto.RegisterUserDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import com.example.libraryapp.NEWdomain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.NEWinfrastructure.persistence.inmemory.InMemoryUserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserFacadeTest {
    private UserFacade userFacade;
    private final TestHelper testHelper = new TestHelper();

    @BeforeEach
    void setUp() {
        userFacade = new UserConfiguration().userFacade(new InMemoryUserRepositoryImpl());
    }


    @Test
    @DisplayName("Should find user by id when user exists")
    void shouldFindUserById() {
        // Given
        RegisterUserDto newUser = testHelper.createNewUser();

        // When
        Long userId = userFacade.registerNewUser(newUser);

        // Then
        UserDto userById = userFacade.getUserById(userId);
        assertNotNull(userById);
        assertEquals(userId, userById.getId());
    }

    @Test
    @DisplayName("Should throw exception when user id does not exist")
    void shouldThrowExceptionWhenUserIdDoesNotExist() {
        // Given
        Long userId = 1L;

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserById(userId));
    }

    @Test
    @DisplayName("Should find user by email when user exists")
    void shouldFindUserByEmail() {
        // Given
        String email = "XXX@gmail.com";
        RegisterUserDto newUser = testHelper.createNewUser();
        newUser.setEmail(email);
        userFacade.registerNewUser(newUser);

        // When
        UserDto userByEmail = userFacade.getUserByEmail(email);

        // Then
        assertNotNull(userByEmail);
        assertEquals(email, userByEmail.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when username does not exist")
    void shouldThrowExceptionWhenUsernameDoesNotExist() {
        // Given
        String username = "listIsEmpty@gmail.com";

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByEmail(username));
    }

    @Test
    @DisplayName("Should create a new user")
    void shouldCreateNewUserSuccessfully() {
        // Given
        RegisterUserDto newUser = testHelper.createNewUser();

        // When
        Long userId = userFacade.registerNewUser(newUser);

        // Then
        UserDto userById = userFacade.getUserById(userId);
        assertNotNull(userById);
        assertEquals(userId, userById.getId());
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // Given
        String email = "repeatedEmail@gmail.com";
        RegisterUserDto newUser1 = testHelper.createNewUser();
        RegisterUserDto newUser2 = testHelper.createNewUser();
        newUser1.setEmail(email);
        newUser2.setEmail(email);
        userFacade.registerNewUser(newUser1);
        MessageSource mockMessageSource = mock(MessageSource.class);

        // When
        Executable executable = () -> userFacade.registerNewUser(newUser2);
        when(mockMessageSource.getMessage(any(), any(), any())).thenReturn("Email already exists");

        // Then
        assertThrows(BadCredentialsException.class, executable);
//        assertEquals("Email already exists", Message.VALIDATION_EMAIL_UNIQUE.getMessage());
    }

}
