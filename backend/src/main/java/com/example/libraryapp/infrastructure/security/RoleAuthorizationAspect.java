package com.example.libraryapp.infrastructure.security;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.domain.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
@Aspect
@RequiredArgsConstructor
public class RoleAuthorizationAspect {
    private static final String ROLE_PREFIX = "ROLE_";
    private final AuthenticationFacade authFacade;

    @Before("within(@com.example.libraryapp.infrastructure.security.RoleAuthorization *) || @annotation(com.example.libraryapp.infrastructure.security.RoleAuthorization)")
    public void checkRole(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RoleAuthorization roleAuthorization = method.getAnnotation(RoleAuthorization.class);

        if (roleAuthorization == null) {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            roleAuthorization = targetClass.getAnnotation(RoleAuthorization.class);
        }

        if (roleAuthorization != null) {
            Role[] roles = roleAuthorization.value();
            validateRoles(roles);
        }
    }

    private void validateRoles(Role[] allowedRoles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("AccessDeniedException");
        } else {
            boolean userIdAdmin = authFacade.isCurrentUserAdmin();
            boolean userIsAllowedToGetData = checkUserHasRole(authentication, allowedRoles) || userIdAdmin;
            if (!userIsAllowedToGetData) {
                throw new ForbiddenAccessException();
            }
        }
    }

    private boolean checkUserHasRole(Authentication authentication, Role[] allowedRoles) {
        List<String> rolesStr = Arrays.stream(allowedRoles).map(Enum::name).toList();
        boolean isUserAccess = rolesStr.contains(Role.USER.name());

        boolean isAdminOrCashierOrWarehouse = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.startsWith(ROLE_PREFIX) ? authority.substring(ROLE_PREFIX.length()) : authority)
                .anyMatch(authority ->
                        authority.equals(Role.ADMIN.name())
                     || authority.equals(Role.CASHIER.name())
                     || authority.equals(Role.WAREHOUSE.name())
                );

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.startsWith(ROLE_PREFIX) ? authority.substring(ROLE_PREFIX.length()) : authority)
                .anyMatch(role -> rolesStr.contains(role) || (isUserAccess && isAdminOrCashierOrWarehouse));
    }
}
