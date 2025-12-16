package com.app.pulse.mappers;

import com.app.pulse.dto.response.ServerResponse;
import com.app.pulse.models.Server;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServerMapper {

    @Mapping(source = "id", target = "serverId")
    ServerResponse toServerResponse(Server server);
}
