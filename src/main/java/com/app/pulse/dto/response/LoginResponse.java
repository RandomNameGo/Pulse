package com.app.pulse.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String displayName;
    private String email;
    private String token;
}
