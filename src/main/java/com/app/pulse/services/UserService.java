package com.app.pulse.services;

import com.app.pulse.dto.request.CreateUserRequest;

public interface UserService {
    String createUser(CreateUserRequest userRequest);
}
