package com.example.libraryapp.domain.user;

import com.example.libraryapp.domain.checkout.Checkout;
import com.example.libraryapp.domain.config.CustomSecurityConfig;
import com.example.libraryapp.domain.exception.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.helper.CardNumGenerator;
import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.user.dto.UserCredentialsDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserRegistrationDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.domain.user.mapper.UserCredentialsDtoMapper;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email).map(UserCredentialsDtoMapper::map);
    }

    @Transactional
    public UserDto registerUserWithDefaultRole(UserRegistrationDto userRegistration) {
        UserRole defaultRole = userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow();
        User user = new User();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.setRole(defaultRole);
        User savedUser = userRepository.save(user);
        user.setCardNumber(CardNumGenerator.generate(savedUser.getId()));
        return UserDtoMapper.map(user);
    }

    public List<UserDto> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserDtoMapper::map)
                .toList();
    }

    public Optional<UserDto> findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDtoMapper::map);
    }

    @Transactional
    public UserDto updateUser(Long id, UserUpdateDto user) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (user != null) {
            if (user.getFirstName() != null) userToUpdate.setFirstName(user.getFirstName());
            if (user.getLastName() != null) userToUpdate.setLastName(user.getLastName());
            if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getCardNumber() != null) userToUpdate.setCardNumber(user.getCardNumber());
            if (user.getRole() != null)  {
                UserRole userRole = userRoleRepository.findByName(user.getRole()).orElseThrow();
                userToUpdate.setRole(userRole);
            }
        } else {
            throw new NullPointerException();
        }
        return UserDtoMapper.map(userToUpdate);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        checkIfUserHasReturnedAllBooks(user);
        setAvailabilityOfUsersReservedBooksToTrue(user);
        userRepository.deleteById(id);
    }

    public Long getCurrentLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new)
                .getId();
    }

    public String getCurrentLoggedInUserRole() {
        Long userId = getCurrentLoggedInUserId();
        return findUserRoleByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkIfUserHasReturnedAllBooks(User user) {
        Optional<Checkout> notReturnedBooks = user.getCheckouts().stream()
                .filter(ch -> !ch.getIsReturned())
                .findAny();
        if (notReturnedBooks.isPresent()) throw new UserHasNotReturnedBooksException();
    }

    private void setAvailabilityOfUsersReservedBooksToTrue(User user) {
        user.getReservations().stream()
                .map(Reservation::getBook)
                .forEach(b -> b.setAvailability(true));
    }
}
