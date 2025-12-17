package com.app.pulse.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectMessageResponse {
    private Long id;
    private String content;
    private Long senderId;
    private String senderName;
    private String senderAvatar;
    private String sentAt;
}
