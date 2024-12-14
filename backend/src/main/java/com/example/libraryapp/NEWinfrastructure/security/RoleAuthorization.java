package com.example.libraryapp.NEWinfrastructure.security;

import com.example.libraryapp.NEWdomain.user.model.Role;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAuthorization {

    Role[] value();
}
