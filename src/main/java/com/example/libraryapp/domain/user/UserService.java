package com.example.libraryapp.domain.user;

import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.domain.config.CustomSecurityConfig;
import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
import com.example.libraryapp.domain.exception.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserModelAssembler userModelAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    public PagedModel<UserDto> findAllUsers(Pageable pageable) {
        Page<User> userDtoPage =
                pageable.isUnpaged() ? new PageImpl<>(userRepository.findAll()) : userRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(userDtoPage, userModelAssembler);
    }

    public Optional<UserDto> findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDtoMapper::map);
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userModelAssembler::toModel);
    }

    public Optional<String> findUserRoleByUserId(Long id) {
        return userRepository.findById(id)
                .map(user -> user.getRole().name());
    }

    @Transactional
    public UserDto updateUser(Long id, UserUpdateDto user) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean currentLoggedInUserRoleIsAdmin = getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

        if (user != null) {
            if (user.getFirstName() != null) userToUpdate.setFirstName(user.getFirstName());
            if (user.getLastName() != null) userToUpdate.setLastName(user.getLastName());
            // TODO: 05.06.2023 Email must be unique!
            if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            // TODO: 05.06.2023 The new card number has to be generated
            if (currentLoggedInUserRoleIsAdmin && user.getCard().isActive()) userToUpdate.setCard(user.getCard());
            if (currentLoggedInUserRoleIsAdmin && user.getRole() != null)  {
                UserRole userRole = UserRole.valueOf(user.getRole());
                userToUpdate.setRole(userRole);
            }
        } else {
            throw new NullPointerException();
        }
        return userModelAssembler.toModel(userToUpdate);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        checkIfUserHasReturnedAllBooks(user);
        user.cancelAllReservations();
        userRepository.deleteById(id);
    }

    public boolean checkIfCurrentLoggedInUserIsAdmin() {
         return checkIfCurrentLoggedInUserIsAdminOrDataOwner(null);
    }

    public boolean checkIfCurrentLoggedInUserIsAdminOrDataOwner(Long userId) {
        boolean isOwner = Objects.equals(getCurrentLoggedInUserId(), userId);
        boolean isAdmin = getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);
        return isOwner || isAdmin;
    }

    private Long getCurrentLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new)
                .getId();
    }

    private String getCurrentLoggedInUserRole() {
        Long userId = getCurrentLoggedInUserId();
        return findUserRoleByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkIfUserHasReturnedAllBooks(User user) {
        Optional<Lending> notReturnedBooks = user.getLendings().stream()
                .filter(ch -> !ch.getIsReturned())
                .findAny();
        if (notReturnedBooks.isPresent()) throw new UserHasNotReturnedBooksException();
    }
}
