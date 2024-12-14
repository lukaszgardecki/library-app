package com.example.libraryapp.NEWdomain.useractivity.ports;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserActivityRepository {

    Optional<UserActivity> findById(Long id);

    Page<UserActivity> findAllByParams(Long userId, String type, Pageable pageable);

    UserActivity save(UserActivity model);
}
