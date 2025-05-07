package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.dto.user.UserAuthDto;
import com.example.userservice.domain.model.user.values.UserId;

public interface AuthenticationServicePort {

    UserAuthDto getUserAuthByUserId(UserId userId);
}
