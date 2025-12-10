package com.app.pulse.services;

import com.app.pulse.dto.request.CreateUserRequest;
import com.app.pulse.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    String createUser(CreateUserRequest userRequest);

    String uploadAvatar(MultipartFile file, long userId) throws IOException;

    UserResponse getUser(long userId);
}
