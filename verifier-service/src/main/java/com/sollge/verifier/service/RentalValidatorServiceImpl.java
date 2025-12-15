package com.sollge.verifier.service;

import com.sollge.verifier.entity.RentalEntity;
import com.sollge.verifier.entity.RentalStatus;
import com.sollge.verifier.repository.RentalRepository;
import com.sollge.verifier.service.validator.ProfanityValidator;
import com.sollge.verifier.service.validator.RentalValidator;
import com.sollge.verifier.service.validator.SymbolValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RentalValidatorServiceImpl implements RentalValidatorService {

    /**
     * List of all Validators for the RentalEntity
     */
    private static final Collection<RentalValidator> VALIDATORS = List.of(
            new SymbolValidator(),
            new ProfanityValidator()
    );

    /**
     * JPA Repository pointing to Mongo Collection storing Rentals
     */
    private final RentalRepository rentalRepository;

    /**
     * Kafka template to notify RentalService about verified rentals
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Runs RentalEntity through multiple Validator checks and if everything is fine, then marks rental entity as available
     * If it fails at any stage, then the rental's status is changed to Blocked.
     * After the validation is completed, a notification is sent to the rental owner
     *
     * @param rentalUUID UUID to find RentalEntity in the database
     * @throws RuntimeException If it couldn't find a RentalEntity by the provided UUID, then the exception is thrown
     */
    @Override
    @Transactional
    public void validateRental(final @NonNull UUID rentalUUID) {
        var rentalEntity = rentalRepository
                .findById(rentalUUID)
                .orElseThrow(() -> new RuntimeException("Couldn't find a rental entity with UUID: " + rentalUUID));

        var isValid = isValid(rentalEntity);
        updateRentalStatus(isValid, rentalEntity);
    }

    /**
     * Changes rental status based on its validity. If a rental was successfully validated, then it's marked as available.
     * Otherwise, it's marked as blocked. After the status is updated, a new notification is sent to the rental owner about the update
     *
     * @param isValid Whether the rental is valid and can be marked as Available
     * @param rentalEntity RentalEntity object representation of a Document in the database
     */
    private void updateRentalStatus(final boolean isValid, final @NonNull RentalEntity rentalEntity) {
        var status = isValid ? RentalStatus.AVAILABLE : RentalStatus.BLOCKED;
        var rentalId = rentalEntity.getId();
        var message = rentalId + ":" + status;

        log.info("Marked rental entity with uuid {} as {} and notified RentalService", rentalId, status);
        kafkaTemplate.send("rental-verified", message);
    }

    /**
     * Validates whether a RentalEntity passes all validators successfully.
     *
     * @param rentalEntity RentalEntity object representation of a Document in the database
     * @return True if all validators passed, false if otherwise
     */
    private boolean isValid(final @NonNull RentalEntity rentalEntity) {
        return VALIDATORS.parallelStream()
                .unordered()
                .allMatch(validator -> validator.validate(rentalEntity));
    }
}
