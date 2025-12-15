package com.sollge.auth.user.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(

        @NotBlank(message = "username is mandatory")
        @Size(min = 6, max = 20)
        String username,

        @NotBlank(message = "password is mandatory")
        @Size(min = 8, max = 300)
        String password
) {
}