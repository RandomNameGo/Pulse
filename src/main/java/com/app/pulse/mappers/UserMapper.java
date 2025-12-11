package com.app.pulse.mappers;

import com.app.pulse.dto.response.UserResponse;
import com.app.pulse.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserResponse toUserResponse(User user);

    User toUser(User user);

}
