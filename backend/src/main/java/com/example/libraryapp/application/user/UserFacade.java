package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.dto.*;
import com.example.libraryapp.domain.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class UserFacade {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserListUseCase getUserListUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserPreviewUseCase getUserPreviewUseCase;
    private final GetUserDetailsUseCase getUserDetailsUseCase;
    private final GetUserDetailsAdminUseCase getUserDetailsAdminUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserByAdminUseCase updateUserByAdminUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final VerifyUserForBookItemRequestUseCase verifyUserForBookItemRequestUseCase;
    private final VerifyUserForBookItemLoanUseCase verifyUserForBookItemLoanUseCase;
    private final VerifyUserForBookItemRenewalUseCase verifyUserForBookItemRenewalUseCase;
    private final CountAllUseCase countAllUseCase;
    private final CountNewRegisteredUsersByMonthUseCase countNewRegisteredUsersByMonthUseCase;
    private final GetUsersByLoanCountDescendingUseCase getUsersByLoanCountDescendingUseCase;

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return getAllUsersUseCase.execute(pageable)
                .map(UserMapper::toDto);
    }

    public List<UserDto> getAllByLoanCountDesc(int limit) {
        return getUsersByLoanCountDescendingUseCase.execute(limit)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public Page<UserListPreviewDto> getUserList(String query, Pageable pageable) {
        return getUserListUseCase.execute(query, pageable)
                .map(UserMapper::toDto);
    }

    public UserDto getUserById(UserId id) {
        User user = getUserUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserDto getUserByEmail(Email email) {
        User user = getUserUseCase.execute(email);
        return UserMapper.toDto(user);
    }

    public UserDetailsDto getUserDetails(UserId id) {
        UserDetails user = getUserDetailsUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserDetailsAdminDto getUserDetailsAdmin(UserId id) {
        UserDetailsAdmin user = getUserDetailsAdminUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserPreviewDto getUserPreview(UserId id) {
        UserPreview userPreview = getUserPreviewUseCase.execute(id);
        return UserMapper.toDto(userPreview);
    }

    public UserId registerNewUser(RegisterUserDto request) {
        return registerUserUseCase.execute(request);
    }

    public UserDto updateUser(UserId id, UserUpdateDto userData) {
        User updatedUser = updateUserUseCase.execute(id, userData);
        return UserMapper.toDto(updatedUser);
    }

    public UserDto updateUserByAdmin(UserId id, UserUpdateAdminDto userData) {
        User updatedUser = updateUserByAdminUseCase.execute(id, userData);
        return UserMapper.toDto(updatedUser);
    }

    public void deleteUserById(UserId userId) {
        deleteUserUseCase.execute(userId);
    }

    public void verifyUserForBookItemRequest(UserId userId) {
        verifyUserForBookItemRequestUseCase.execute(userId);
    }

    public void verifyUserForBookItemLoan(UserId userId) {
        verifyUserForBookItemLoanUseCase.execute(userId);
    }

    public void verifyUserForBookItemRenewal(UserId userId) {
        verifyUserForBookItemRenewalUseCase.execute(userId);
    }

    public long countAllUsers() {
        return countAllUseCase.execute();
    }
    public long countNewRegisteredUsersByMonth(int month, int year) {
        return countNewRegisteredUsersByMonthUseCase.execute(month, year);
    }

    public void generateFakeUsers(int limit) {
        for (int i = 0; i < limit; i++) {
            RegisterUserDto user = FakeUserGenerator.generate();
            registerUserUseCase.execute(user);
        }
    }
}
