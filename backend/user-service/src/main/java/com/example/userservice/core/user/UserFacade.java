package com.example.userservice.core.user;

import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserUpdate;
import com.example.userservice.domain.model.user.UserUpdateAdmin;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class UserFacade {
    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final VerifyUserForBookItemRequestUseCase verifyUserForBookItemRequestUseCase;
    private final VerifyUserForBookItemLoanUseCase verifyUserForBookItemLoanUseCase;
    private final VerifyUserForBookItemRenewalUseCase verifyUserForBookItemRenewalUseCase;
    private final CountAllUseCase countAllUseCase;
    private final CountNewRegisteredUsersByMonthUseCase countNewRegisteredUsersByMonthUseCase;
    private final GetUsersByLoanCountDescendingUseCase getUsersByLoanCountDescendingUseCase;

    public Page<User> getAllUsers(Pageable pageable) {
        return getAllUsersUseCase.execute(pageable);
    }

    public Page<User> getAllUsersByQuery(String query, Pageable pageable) {
        return getAllUsersUseCase.execute(query, pageable);
    }

    public List<User> getAllByLoanCountDesc(int limit) {
        return getUsersByLoanCountDescendingUseCase.execute(limit);
    }

    public User getUserById(UserId id) {
        return getUserUseCase.execute(id);
    }

    public User getUserByPersonId(PersonId personId) {
        return getUserUseCase.execute(personId);
    }

    public UserId createNewUser(PersonId personId) {
        return createUserUseCase.execute(personId);
    }

    public User updateUser(UserId id, UserUpdate userData) {
        return updateUserUseCase.execute(id, userData);
    }

    public User updateUserByAdmin(UserId id, UserUpdateAdmin userData) {
        return updateUserUseCase.execute(id, userData);
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
}
