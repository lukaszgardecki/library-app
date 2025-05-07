package com.example.authservice.core.registration;

import com.example.authservice.domain.dto.auth.RegisterToSaveDto;
import com.example.authservice.domain.dto.auth.RegisterUserDto;
import com.example.authservice.domain.model.authdetails.*;
import com.example.authservice.domain.ports.EventPublisherPort;
import com.example.authservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final RegistrationService registrationService;
    private final UserServicePort userService;
    private final EventPublisherPort publisher;

    void execute(RegisterToSaveDto request) {
        Email email = new Email(request.username());
        registrationService.validateEmail(email);
        RegisterUserDto userDataToSave = toRegisterUserDto(request);
        UserId userId = userService.register(userDataToSave);
        registrationService.save(email, new Password(request.password()), userId);
        publisher.publishUserCreatedEvent(userId);
    }

    private RegisterUserDto toRegisterUserDto(RegisterToSaveDto register) {
        return new RegisterUserDto(
                register.firstName(),
                register.lastName(),
                register.pesel(),
                register.dateOfBirth(),
                register.gender(),
                register.nationality(),
                register.mothersName(),
                register.fathersName(),
                register.streetAddress(),
                register.zipCode(),
                register.city(),
                register.state(),
                register.country(),
                register.phone()
        );
    }
}