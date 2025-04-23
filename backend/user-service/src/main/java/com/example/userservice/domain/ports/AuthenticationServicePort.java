package com.example.userservice.domain.ports;

import com.example.userservice.domain.dto.user.UserAuthDto;
import com.example.userservice.domain.model.user.UserId;

public interface AuthenticationServicePort {

    UserAuthDto getUserAuthByUserId(UserId userId);
}
