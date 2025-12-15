package com.sollge.rental.controller;

import com.sollge.rental.dto.RentalResponseDTO;
import com.sollge.rental.dto.RentalSubmitDTO;
import com.sollge.rental.entity.RentalStatus;
import com.sollge.rental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalControllerV1 {

    private final RentalService rentalService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable UUID id) {
        return ResponseEntity.of(rentalService.getRentalById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RentalResponseDTO>> getRentals(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(rentalService.getRentalsByStatus(RentalStatus.AVAILABLE, pageable));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalResponseDTO> submitRental(
            @Valid @RequestBody RentalSubmitDTO rentalDTO,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(rentalService.submitRental(rentalDTO, userId));
    }
}