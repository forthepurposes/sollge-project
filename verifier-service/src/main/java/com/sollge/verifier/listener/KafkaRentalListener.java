package com.sollge.verifier.listener;

import com.sollge.verifier.service.RentalValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaRentalListener {

    private final RentalValidatorService validatorService;

    /**
     * When a user submits a rental to the RentalService,
     * It sends a kafka message to the verify-rental topic to let VerifierService verify the rental
     */
    @KafkaListener(topics = "verify-rental", groupId = "verify-rental")
    public void onVerifyRental(UUID rentalUUID) {
        log.info("Received a new Rental for verification with a UUID {}", rentalUUID);
        validatorService.validateRental(rentalUUID);
    }
}
