package com.sollge.verifier.service.validator;

import com.sollge.verifier.entity.RentalEntity;
import org.springframework.lang.NonNull;

import java.util.regex.Pattern;

/**
 * Makes sure that users don't throw a bunch of nonsense symbols in a description or title of a Rental
 */
public class SymbolValidator implements RentalValidator {

    private static final Pattern CORRECT_SYMBOLS_PATTERN = Pattern.compile("^[a-zA-Z0-9 .,!?\"'()\\-]*$");

    @Override
    public boolean validate(final @NonNull RentalEntity rental) {
        String result = rental.getTitle() + rental.getDescription();
        return CORRECT_SYMBOLS_PATTERN.matcher(result).matches();
    }
}
