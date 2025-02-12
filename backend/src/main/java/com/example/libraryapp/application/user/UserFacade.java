package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.dto.*;
import com.example.libraryapp.domain.user.model.AdminUserDetails;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserListPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class UserFacade {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserAdminInfoUseCase getUserAdminInfoUseCase;
    private final GetUserListPreviewUseCase getUserListPreviewUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserByAdminUseCase updateUserByAdminUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final VerifyUserForBookItemRequestUseCase verifyUserForBookItemRequestUseCase;
    private final VerifyUserForBookItemLoanUseCase verifyUserForBookItemLoanUseCase;
    private final VerifyUserForBookItemRenewalUseCase verifyUserForBookItemRenewalUseCase;
    private final CountAllUseCase countAllUseCase;
    private final CountNewRegisteredUsersByMonthUseCase countNewRegisteredUsersByMonthUseCase;
    private final GetUsersByLoanCountDescendingUseCase getUsersByLoanCountDescendingUseCase;
    private final SearchUserPreviewsUseCase searchUserPreviewsUseCase;

    public List<UserDto> getAllByLoanCountDesc(int limit) {
        return getUsersByLoanCountDescendingUseCase.execute(limit)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public Page<UserListPreviewDto> searchUserPreviews(String query, Pageable pageable) {
        return searchUserPreviewsUseCase.execute(query, pageable)
                .map(UserMapper::toDto);
    }

    public UserDto getUserById(Long id) {
        User user = getUserUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = getUserUseCase.execute(email);
        return UserMapper.toDto(user);
    }

    public AdminUserDetailsDto getUserAdminInfo(Long id) {
        AdminUserDetails user = getUserAdminInfoUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserListPreviewDto getUserListPreviewById(Long userId) {
        UserListPreview userListPreview = getUserListPreviewUseCase.execute(userId);
        return UserMapper.toDto(userListPreview);
    }

    public Long registerNewUser(RegisterUserDto request) {
        return registerUserUseCase.execute(request);
    }

    public UserDto updateUser(Long id, UserUpdateDto userData) {
        User updatedUser = updateUserUseCase.execute(id, userData);
        return UserMapper.toDto(updatedUser);
    }

    public UserDto updateUserByAdmin(Long id, UserUpdateAdminDto userData) {
        User updatedUser = updateUserByAdminUseCase.execute(id, userData);
        return UserMapper.toDto(updatedUser);
    }

    public void deleteUserById(Long userId) {
        deleteUserUseCase.execute(userId);
    }

    public void verifyUserForBookItemRequest(Long userId) {
        verifyUserForBookItemRequestUseCase.execute(userId);
    }

    public void verifyUserForBookItemLoan(Long userId) {
        verifyUserForBookItemLoanUseCase.execute(userId);
    }

    public void verifyUserForBookItemRenewal(Long userId) {
        verifyUserForBookItemRenewalUseCase.execute(userId);
    }

    public long countAllUsers() {
        return countAllUseCase.execute();
    }
    public long countNewRegisteredUsersByMonth(int month, int year) {
        return countNewRegisteredUsersByMonthUseCase.execute(month, year);
    }
}
