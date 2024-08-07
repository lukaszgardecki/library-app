package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.exception.auth.ForbiddenAccessException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.member.Role;
import com.example.libraryapp.management.Message;
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
import java.util.Optional;

@Component
@Aspect
@RequiredArgsConstructor
public class RoleAuthorizationAspect {
    private static final String ROLE_PREFIX = "ROLE_";
    private final MemberRepository memberRepository;

    @Before("within(@RoleAuthorization *) || @annotation(RoleAuthorization)")
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
            throw new AccessDeniedException(Message.ACCESS_DENIED.getMessage());
        } else {
            boolean userIsAllowedToGetData = checkUserHasRole(authentication, allowedRoles) || isMemberAdmin();
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

    private Long getCurrentLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findMemberByEmail(username).getId();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    public boolean isMemberAdmin() {
        return getCurrentLoggedInUserRole().equals(Role.ADMIN.name());
    }

    private String getCurrentLoggedInUserRole() {
        Long userId = getCurrentLoggedInUserId();
        return findMemberRoleByUserId(userId)
                .orElseThrow(() -> new MemberNotFoundException(userId));
    }

    private Optional<String> findMemberRoleByUserId(Long id) {
        return memberRepository.findById(id)
                .map(user -> user.getRole().name());
    }
}
