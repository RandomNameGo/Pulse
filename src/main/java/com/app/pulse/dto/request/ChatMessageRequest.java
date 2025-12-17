package com.app.pulse.dto.request;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private long senderId;
    private long receiverId;
    private String content;
}
