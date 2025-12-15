package com.sollge.auth.user.dto.auth;

public record TokenValidatedResponseDTO(
        String username,
        Long userId
) {
}
