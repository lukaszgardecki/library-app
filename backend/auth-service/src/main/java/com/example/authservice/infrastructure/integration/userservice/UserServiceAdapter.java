package com.example.authservice.infrastructure.integration.userservice;

import com.example.authservice.domain.dto.PersonDto;
import com.example.authservice.domain.dto.auth.RegisterUserDto;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.ports.out.UserServicePort;
import com.example.authservice.infrastructure.integration.FeignClientCustomConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@RequiredArgsConstructor
class UserServiceAdapter implements UserServicePort {
    private final UserServiceFeignClient client;

    @Override
    public UserId register(RegisterUserDto userData) {
        ResponseEntity<Long> response = client.register(userData);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new UserId(response.getBody());
        }
        return null;
    }

    @Override
    public PersonDto getPersonByUser(Long userId) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(FeignClientCustomConfiguration.USER_ID_ATTR_NAME, userId, RequestAttributes.SCOPE_REQUEST);
        ResponseEntity<PersonDto> response = client.getPersonByUserId(userId);
        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } finally {
            requestAttributes.removeAttribute(FeignClientCustomConfiguration.USER_ID_ATTR_NAME, RequestAttributes.SCOPE_REQUEST);
        }

        return null;
    }
}
