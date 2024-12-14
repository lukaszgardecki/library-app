package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.dto.RegisterUserDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import com.example.libraryapp.NEWdomain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFacade {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserAfterBookItemRequestUseCase updateUserAfterBookItemRequestUseCase;
    private final UpdateUserAfterBookItemLoanUseCase updateUserAfterBookItemLoanUseCase;
    private final UpdateUserAfterBookItemReturnUseCase updateUserAfterBookItemReturnUseCase;
    private final VerifyUserForBookItemRequestUseCase verifyUserForBookItemRequestUseCase;
    private final VerifyUserForBookItemLoanUseCase verifyUserForBookItemLoanUseCase;
    private final VerifyUserForBookItemRenewalUseCase verifyUserForBookItemRenewalUseCase;

    public UserDto getUserById(Long id) {
        User user = getUserUseCase.execute(id);
        return UserMapper.toDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = getUserUseCase.execute(email);
        return UserMapper.toDto(user);
    }

    public Long registerNewUser(RegisterUserDto request) {
        Long savedUserId = registerUserUseCase.execute(request);
        // TODO: 04.12.2024 pwoiadom action (event)
        // TODO: 04.12.2024 takie coś powinno się wykonać w module action
//        actionService.save(new ActionRegister(savedMemberDto));
        return savedUserId;
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

    public void updateUserAfterBookItemRequest(Long userId) {
        updateUserAfterBookItemRequestUseCase.execute(userId);
    }

    public void updateUserAfterBookItemLoan(Long userId) {
        updateUserAfterBookItemLoanUseCase.execute(userId);
    }

    public void updateUserAfterBookItemReturn(Long userId) {
        updateUserAfterBookItemReturnUseCase.execute(userId);
    }
}
