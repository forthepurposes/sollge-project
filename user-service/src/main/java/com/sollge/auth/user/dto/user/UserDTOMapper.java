package com.sollge.auth.user.dto.user;

import com.sollge.auth.user.model.UserEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<UserEntity, UserDTO> {

    @Override
    public @NonNull UserDTO apply(final @NonNull UserEntity user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}