package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.member.Role;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAuthorization {

    Role[] value();
}
