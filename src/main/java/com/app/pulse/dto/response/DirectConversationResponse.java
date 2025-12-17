package com.app.pulse.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class DirectConversationResponse {

    private long directConversationId;
    private long user1Id;
    private long user2Id;
    private Instant updatedAt;

}
