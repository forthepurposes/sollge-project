package com.sollge.rental.repository;

import com.sollge.rental.entity.RentalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        rentalRepository.deleteAll();
    }

    @Test
    @DisplayName("Test")
    void givenRentalObject_whenSave_thenRentalIsCreated() {
        var rentalEntity = RentalEntity.builder()
                .ownerId(1L)
                .title("Title")
                .description("Description")
                .price(100L)
                .imagesNames(new String[] {"one.png"})
                .build();

        rentalRepository.save(rentalEntity);
        UUID id = rentalEntity.getId();

        assertTrue(rentalRepository.findById(id).isPresent());
    }
}
