package com.app.pulse.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ServerResponse {
    private long serverId;
    private String name;
    private String iconUrl;
    private UserResponse owner;
}
