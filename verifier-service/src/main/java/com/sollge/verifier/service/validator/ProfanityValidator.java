package com.sollge.verifier.service.validator;

import com.sollge.verifier.entity.RentalEntity;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Makes sure users don't put swearing words in a description or title of a Rental.
 * Overall, this is a very slow performing validator. Since the project doesn't intend to be 100% safe, it's good enough.
 * A better approach would be to use the Aho–Corasick algorithm for bad word detection
 */
public class ProfanityValidator implements RentalValidator {

    private static final Collection<String> PROFANITY_DICTIONARY = Set.of("bitch", "fuck", "shit", "retard");

    @Override
    public boolean validate(final @NonNull RentalEntity rental) {
        String result = rental.getTitle() + rental.getDescription();
        return PROFANITY_DICTIONARY.stream().noneMatch(result::contains);
    }
}
