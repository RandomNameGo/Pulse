package com.app.pulse.controllers;


import com.app.pulse.dto.request.CreateUserRequest;
import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.services.EmailService;
import com.app.pulse.services.OtpService;
import com.app.pulse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1/user")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    private final OtpService otpService;

    @PostMapping("/verify")
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request) {
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

}
