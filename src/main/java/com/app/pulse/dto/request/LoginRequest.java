package com.app.pulse.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
