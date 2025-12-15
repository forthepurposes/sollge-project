package com.sollge.verifier.service.validator;

import com.sollge.verifier.entity.RentalEntity;
import org.jetbrains.annotations.NotNull;

public interface RentalValidator {

    /**
     * Checks whether the provided RentalEntity meets the validator requirements
     *
     * @param rental RentalEntity
     * @return True if everything is ok, false if one is validators failed
     */
    boolean validate(@NotNull RentalEntity rental);
}
