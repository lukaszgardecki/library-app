package com.example.authservice.core.registration;

import com.example.authservice.domain.dto.auth.RegisterToSaveDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationFacade {
    private final RegisterUserUseCase registerUserUseCase;

    public void registerUser(RegisterToSaveDto request) {
        registerUserUseCase.execute(request);
    }

    public void generateFakeUsers(int limit) {
        for (int i = 0; i < limit; i++) {
            RegisterToSaveDto user = FakeUserGenerator.generate();
            registerUserUseCase.execute(user);
        }
    }
}
