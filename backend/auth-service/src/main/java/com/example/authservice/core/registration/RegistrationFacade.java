package com.example.authservice.core.registration;

import com.example.authservice.domain.model.user.RegisterToSave;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationFacade {
    private final RegisterUserUseCase registerUserUseCase;

    public void registerUser(RegisterToSave request) {
        registerUserUseCase.execute(request);
    }

    public void generateFakeUsers(int limit) {
        for (int i = 0; i < limit; i++) {
            RegisterToSave user = FakeUserGenerator.generate();
            registerUserUseCase.execute(user);
        }
    }
}
