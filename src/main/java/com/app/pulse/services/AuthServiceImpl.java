package com.app.pulse.services;

import com.app.pulse.dto.request.LoginRequest;
import com.app.pulse.dto.response.LoginResponse;
import com.app.pulse.models.User;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public LoginResponse Login(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            return null;
        }

        if(passwordEncoder.matches(loginRequest.getPassword(), user.get().getPasswordHash())){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(user.get().getId());
            loginResponse.setUsername(user.get().getUsername());
            loginResponse.setDisplayName(user.get().getDisplayName());
            loginResponse.setEmail(user.get().getEmail());
            String token = jwtService.generateToken(user.get());
            loginResponse.setToken(token);
            return loginResponse;
        }

        return null;
    }
}
