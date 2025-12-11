package com.app.pulse.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class FriendRequestResponse {
    private long id;
    private String sender;
    private Instant sendAt;
}
