package com.app.pulse.services;

import com.app.pulse.dto.response.ChannelResponse;

import java.util.List;

public interface ChannelService {

    List<ChannelResponse> getChannelByServer(long serverId);

    String createChannel(long serverId, String name, String type);

}
