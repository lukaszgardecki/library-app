package com.example.activityservice.domain.ports.out;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActivityRepositoryPort {

    Optional<Activity> findById(ActivityId id);

    Page<Activity> findAllByParams(UserId userId, String type, Pageable pageable);

    Activity save(Activity model);
}
