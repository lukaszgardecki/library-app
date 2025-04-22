package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.dto.UserAuthDto;
import com.example.userservice.user.domain.model.user.UserId;

public interface AuthenticationServicePort {

    UserAuthDto getUserAuthByUserId(UserId userId);
}
