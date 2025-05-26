package com.example.paymentservice.infrastructure.security;

import com.example.paymentservice.domain.i18n.MessageKey;
import com.example.paymentservice.domain.model.values.UserId;
import com.example.paymentservice.domain.ports.out.MessageProviderPort;
import com.example.paymentservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SourceValidatorAdapter implements SourceValidatorPort {
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
