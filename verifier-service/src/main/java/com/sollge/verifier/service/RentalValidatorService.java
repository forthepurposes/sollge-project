package com.sollge.verifier.service;

import org.springframework.lang.NonNull;

import java.util.UUID;

public interface RentalValidatorService {

    /**
     * Runs RentalEntity through multiple Validator checks and if everything is fine, then marks rental entity as available
     * If it fails at any stage, then the rental's status is changed to Blocked.
     * After the validation is completed, a notification is sent to the rental owner
     *
     * @param rentalUUID UUID to find RentalEntity in the database
     * @throws RuntimeException If it couldn't find a RentalEntity by the provided UUID, then the exception is thrown
     */
    void validateRental(final @NonNull UUID rentalUUID);
}
