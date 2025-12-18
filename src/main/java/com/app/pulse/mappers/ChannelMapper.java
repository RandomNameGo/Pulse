package com.app.pulse.mappers;

import com.app.pulse.dto.response.ChannelResponse;
import com.app.pulse.models.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    @Mapping(source = "id", target = "channelId")
    ChannelResponse toChannelResponse(Channel channel);
}
