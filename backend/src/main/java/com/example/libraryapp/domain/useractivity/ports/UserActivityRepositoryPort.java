package com.example.libraryapp.domain.useractivity.ports;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserActivityRepositoryPort {

    Optional<UserActivity> findById(UserActivityId id);

    Page<UserActivity> findAllByParams(UserId userId, String type, Pageable pageable);

    UserActivity save(UserActivity model);
}
