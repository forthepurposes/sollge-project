package com.sollge.gateway.dto;

public record TokenValidatedResponseDTO(
        String username,
        Long userId
) {
}
