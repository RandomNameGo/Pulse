package com.app.pulse.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private long userId;
    private String username;
    private String displayName;
    private String email;
    private String tag;
    private String avatarUrl;

}
