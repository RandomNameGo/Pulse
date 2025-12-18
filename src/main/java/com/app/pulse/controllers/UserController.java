package com.app.pulse.controllers;


import com.app.pulse.dto.request.CreateUserRequest;
import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.dto.response.UserResponse;
import com.app.pulse.services.EmailService;
import com.app.pulse.services.OtpService;
import com.app.pulse.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    private final OtpService otpService;

    @PostMapping("/user/verify")
    public ResponseEntity<?> verify(@RequestParam("email") String email){
        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data("OTP has been sent to email. Please verify to complete registration.")
                .build()
        );
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserRequest request) {
        if(otpService.verifyOtp(request.getEmail(), request.getOtp())){
            return ResponseEntity.ok().body(ApiResponse.<String>builder()
                    .success(true)
                    .message("Success")
                    .data(userService.createUser(request))
                    .build()
            );
        }
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Fail")
                .data("Can't register user")
                .build()
        );
    }

    @PostMapping("/user/upload-avatar/{userId}")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable Long userId) throws IOException {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(userService.uploadAvatar(file, userId))
                .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Success")
                .data(userService.getUser(userId))
                .build()
        );
    }

}
