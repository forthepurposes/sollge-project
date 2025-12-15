package com.sollge.rental.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document(collection = "rentals")
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Indexed
    private Long ownerId;

    @Indexed
    private Long price;

    @Builder.Default
    private Long views = 0L;

    private String title;
    private String description;
    private String[] imagesNames;

    @Builder.Default
    private RentalStatus status = RentalStatus.REVIEWING;

    @Builder.Default
    private Instant createdAt = Instant.now();
}