package com.sollge.verifier.service;

import net.sollge.verifierservice.entity.RentalEntity;
import net.sollge.verifierservice.entity.RentalStatus;
import net.sollge.verifierservice.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@Import(RentalValidatorServiceImpl.class)
public class RentalValidatorServiceTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

    @DynamicPropertySource
    static void mongoDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalValidatorService rentalValidatorService;

    @BeforeEach
    void setUp() {
        rentalRepository.deleteAll();
    }

    @Test
    public void testRentalValidatorDoesntThrowException_WhenEverythingIsFine() {
        var rentalEntity = RentalEntity.builder()
                .ownerId(100L)
                .title("A normal title")
                .description("A normal description")
                .price(123L)
                .build();

        rentalRepository.save(rentalEntity);
        assertDoesNotThrow(() -> rentalValidatorService.validateRental(rentalEntity.getId()));
    }

    @Test
    public void testRentalValidatorBlocksRental_WhenTitleOrDescriptionAreContainingProfanity() {
        var rentalEntity = RentalEntity.builder()
                .ownerId(100L)
                .title("fuck title")
                .description("shit description")
                .price(123L)
                .build();

        rentalRepository.save(rentalEntity);
        rentalValidatorService.validateRental(rentalEntity.getId());

        var updatedRentalEntity = rentalRepository
                .findById(rentalEntity.getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        assertEquals(RentalStatus.BLOCKED, updatedRentalEntity.getStatus());
    }

    @Test
    public void testRentalValidatorMarksRentalEntityAvailable_WhenAllValidationsPassed() {
        var rentalEntity = RentalEntity.builder()
                .ownerId(100L)
                .title("A completely fine title")
                .description("A completely fine description")
                .price(123L)
                .build();

        rentalRepository.save(rentalEntity);
        rentalValidatorService.validateRental(rentalEntity.getId());

        var updatedRentalEntity = rentalRepository
                .findById(rentalEntity.getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        assertEquals(RentalStatus.AVAILABLE, updatedRentalEntity.getStatus());
    }

    @Test
    public void testRentalValidatorMarksRentalEntityBlocked_WhenSymbolValidationFails() {
        var rentalEntity = RentalEntity.builder()
                .ownerId(100L)
                .title("A completely fine titleദ്ദി ˉ͈̀꒳ˉ͈́ )✧ ✧ദ…")
                .description("A completely fine descriptionദ്ദി ˉ͈̀꒳ˉ͈́ )✧ ✧ദ…")
                .price(123L)
                .build();

        rentalRepository.save(rentalEntity);
        rentalValidatorService.validateRental(rentalEntity.getId());

        var updatedRentalEntity = rentalRepository
                .findById(rentalEntity.getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        assertEquals(RentalStatus.BLOCKED, updatedRentalEntity.getStatus());
    }

    @Test
    public void testRentalValidatorThrowsException_WhenNoRentalEntityFound() {
        assertThrows(RuntimeException.class, () -> rentalValidatorService.validateRental(UUID.randomUUID()));
    }
}
