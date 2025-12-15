package com.sollge.verifier.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@Document(collection = "rentals")
public class RentalEntity {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private Long ownerId;
    private Long price;

    private String title;
    private String description;

    @Builder.Default
    private RentalStatus status = RentalStatus.REVIEWING;

    @Builder.Default
    private Instant createdAt = Instant.now();
}