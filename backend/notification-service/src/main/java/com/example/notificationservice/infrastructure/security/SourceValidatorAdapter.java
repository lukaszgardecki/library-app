package com.example.notificationservice.infrastructure.security;

import com.example.notificationservice.domain.MessageKey;
import com.example.notificationservice.domain.model.UserId;
import com.example.notificationservice.domain.ports.MessageProviderPort;
import com.example.notificationservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SourceValidatorAdapter implements SourceValidator {
    private final MessageProviderPort msgProvider;

    @Override
    public void validateUserIsOwner(UserId userId) {
        Long actualUserId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
        if (!userId.value().equals(actualUserId) && !role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException(msgProvider.getMessage(MessageKey.FORBIDDEN));
        }
    }
}
