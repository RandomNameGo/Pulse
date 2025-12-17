package com.app.pulse.dto.response;

import lombok.Data;

@Data
public class ChannelResponse {
    private long channelId;
    private String name;
    private String type;
}
