package com.sollge.rental.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record RentalSubmitDTO(
        @NotBlank(message = "Title is mandatory")
        @Size(min = 1, max = 50)
        String title,

        @NotBlank(message = "Description is mandatory")
        @Size(min = 10, max = 500)
        String description,

        @NotNull(message = "Price is mandatory")
        @Positive
        @Min(value = 1)
        @Max(value = 100_000_000)
        Long price,

        MultipartFile[] images
) {

}
