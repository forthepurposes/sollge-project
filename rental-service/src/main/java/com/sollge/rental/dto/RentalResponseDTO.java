package com.sollge.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String[] imagesLinks;
    private Long ownerId;
    private Long price;
    private Long views;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;
}