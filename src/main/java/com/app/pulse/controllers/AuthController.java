package com.app.pulse.controllers;

import com.app.pulse.dto.request.LoginRequest;
import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.dto.response.LoginResponse;
import com.app.pulse.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if(authService.Login(loginRequest) != null){
            return ResponseEntity.ok().body(ApiResponse.<LoginResponse>builder()
                    .success(true)
                    .message("Success")
                    .data(authService.Login(loginRequest))
                    .build()
            );
        }

        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Fail")
                .data("Invalid email or password")
                .build()
        );
    }
}
