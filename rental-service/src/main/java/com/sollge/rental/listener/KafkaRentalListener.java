package com.sollge.rental.listener;

import com.sollge.rental.entity.RentalStatus;
import com.sollge.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaRentalListener {

    private final RentalRepository rentalRepository;

    /**
     * When VerifierService marks RentalEntity as available,
     * It sends a kafka message to the rental-verified topic to let RentalService change the state of the RentalEntity in the database
     */
    @KafkaListener(topics = "rental-verified", groupId = "rental-verified")
    public void onVerifyRental(String payload) {
        var args = payload.split(":");
        var rentalId = UUID.fromString(args[0]);
        var status = RentalStatus.valueOf(args[1]);

        log.info("Received a new Rental with uuid {} to set a new status {}", rentalId, status);
        updateRentalStatus(rentalId, status);
    }

    @CachePut(cacheNames = "rentals", key = "#id")
    public void updateRentalStatus(final @NonNull UUID id, final @NonNull RentalStatus status) {
        var rentalEntity = rentalRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find a rental entity with UUID: " + id));

        rentalEntity.setStatus(status);
        rentalRepository.save(rentalEntity);
        log.info("Rental successfully marked as {} with uuid {}", status, id);
    }
}
