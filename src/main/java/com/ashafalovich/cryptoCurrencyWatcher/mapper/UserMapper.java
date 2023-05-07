package com.ashafalovich.cryptoCurrencyWatcher.mapper;

import com.ashafalovich.cryptoCurrencyWatcher.dto.UserRequest;
import com.ashafalovich.cryptoCurrencyWatcher.dto.UserResponse;
import com.ashafalovich.cryptoCurrencyWatcher.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    User toUser(UserRequest dto);

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "message", ignore = true)
    UserResponse toResponse(User user);

}
