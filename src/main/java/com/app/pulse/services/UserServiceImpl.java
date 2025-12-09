package com.app.pulse.services;

import com.app.pulse.dto.request.CreateUserRequest;
import com.app.pulse.models.User;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
}
