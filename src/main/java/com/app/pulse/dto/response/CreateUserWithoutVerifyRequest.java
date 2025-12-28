package com.app.pulse.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserWithoutVerifyRequest {

    @NotNull(message = "Display name must not be null")
    private String displayName;

    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Email must not be null")
    @Email(message = "Email must be correct the format")
    private String email;

}
