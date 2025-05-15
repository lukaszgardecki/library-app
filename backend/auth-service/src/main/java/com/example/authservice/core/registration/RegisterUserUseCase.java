package com.example.authservice.core.registration;

import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.user.RegisterToSave;
import com.example.authservice.domain.ports.out.EventPublisherPort;
import com.example.authservice.domain.ports.out.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final RegistrationService registrationService;
    private final UserServicePort userService;
    private final EventPublisherPort publisher;

    void execute(RegisterToSave request) {
        registrationService.validateEmail(request.username());
        UserId userId = userService.register(request.person());
        registrationService.save(request.username(), request.password(), userId);
        publisher.publishUserCreatedEvent(userId);
    }
}