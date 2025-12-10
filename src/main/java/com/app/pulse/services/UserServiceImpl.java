package com.app.pulse.services;

import com.app.pulse.dto.request.CreateUserRequest;
import com.app.pulse.dto.response.UserResponse;
import com.app.pulse.models.User;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CloudinaryService cloudinaryService;

    @Override
    public String createUser(CreateUserRequest userRequest) {

        if(userRepository.existsByDisplayName(userRequest.getDisplayName())) {
            return "Display name is unavailable";
        }

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return "This Email was used to register user";
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        String tag = String.valueOf(new Random().nextInt(9999));

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setDisplayName(userRequest.getDisplayName());
        user.setEmail(userRequest.getEmail());
        user.setPasswordHash(encodedPassword);
        user.setTag(tag);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        return "Created user '" + userRequest.getUsername() + "' successfully";
    }

    @Override
    public String uploadAvatar(MultipartFile file, long userId) throws IOException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            String avatarUrl = cloudinaryService.uploadFile(file);
            user.get().setAvatarUrl(avatarUrl);
            userRepository.save(user.get());
            return "Avatar uploaded successfully";
        }
        return "Failed to upload avatar";
    }

    @Override
    public UserResponse getUser(long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.get().getId());
            userResponse.setUsername(user.get().getUsername());
            userResponse.setDisplayName(user.get().getDisplayName());
            userResponse.setEmail(user.get().getEmail());
            userResponse.setTag(user.get().getTag());
            userResponse.setAvatarUrl(user.get().getAvatarUrl());
            return userResponse;
        }

        return null;
    }
}
