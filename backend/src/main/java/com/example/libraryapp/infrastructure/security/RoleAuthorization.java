package com.example.libraryapp.infrastructure.security;

import com.example.libraryapp.domain.user.model.Role;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAuthorization {

    Role[] value();
}
