package com.sollge.rental.service;

import com.sollge.rental.dto.RentalResponseDTO;
import com.sollge.rental.dto.RentalSubmitDTO;
import com.sollge.rental.dto.mapper.RentalResponseDTOMapper;
import com.sollge.rental.entity.RentalEntity;
import com.sollge.rental.entity.RentalStatus;
import com.sollge.rental.repository.RentalRepository;
import com.sollge.rental.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalService {

    private static final int MAX_PUBLISHED_RENTALS = 10;

    private final FileService fileService;
    private final ViewCounterService viewCounterService;

    private final RentalRepository rentalRepository;
    private final RentalResponseDTOMapper rentalResponseMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public @NonNull RentalResponseDTO submitRental(final @NonNull RentalSubmitDTO rentalDTO, final @NonNull Long userId) {
        long userRentalsPublished = rentalRepository.countByOwnerIdEquals(userId);
        if (userRentalsPublished > MAX_PUBLISHED_RENTALS) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Unable to publish more than 10 rentals at once");
        }

        String[] imagesNames = getImagesNames(rentalDTO);
        RentalEntity rentalEntity = RentalEntity.builder()
                .ownerId(userId)
                .title(rentalDTO.title())
                .description(rentalDTO.description())
                .price(rentalDTO.price())
                .imagesNames(imagesNames)
                .build();

        rentalRepository.save(rentalEntity);
        kafkaTemplate.send("verify-rental", rentalEntity.getId());

        return rentalResponseMapper.apply(rentalEntity);
    }

    @Cacheable(cacheNames = "rentals", key = "#id")
    public @NonNull Optional<RentalResponseDTO> getRentalById(final @NonNull UUID id) {
        Optional<RentalEntity> rentalEntity = rentalRepository.findById(id);
        rentalEntity.ifPresent(viewCounterService::increment);

        return rentalEntity.map(rentalResponseMapper);
    }

    @Cacheable(cacheNames = "rentals")
    public @NonNull Collection<RentalResponseDTO> getRentals(final @NonNull Pageable pageable) {
        return rentalRepository.findAll(pageable).stream()
                .map(rentalResponseMapper)
                .toList();
    }

    @Cacheable(cacheNames = "rentals")
    public @NonNull Collection<RentalResponseDTO> getRentalsByStatus(
            final @NonNull RentalStatus status,
            final @NonNull Pageable pageable
    ) {
        return rentalRepository.findAllByStatus(status, pageable).stream()
                .map(rentalResponseMapper)
                .toList();
    }

    private @NonNull String[] getImagesNames(final @NonNull RentalSubmitDTO rental) {
        MultipartFile[] images = rental.images();
        if (images == null || images.length == 0) {
            return new String[0];
        }

        if (images.length > 10) {
            throw new HttpClientErrorException(HttpStatus.PAYLOAD_TOO_LARGE, "Unable to upload more than 10 images at once");
        }

        boolean isSizeInvalid = Arrays.stream(images).anyMatch(file -> file.getSize() > ImageUtil.getMaxFileSize());
        if (!isSizeInvalid) {
            throw new HttpClientErrorException(HttpStatus.PAYLOAD_TOO_LARGE, "One of the images is too large");
        }

        return Arrays.stream(images)
                .parallel()
                .map(this::saveImageAndGenerateName)
                .toArray(String[]::new);
    }

    private @NonNull String saveImageAndGenerateName(final @NonNull MultipartFile image) {
        try {
            String imageName = System.currentTimeMillis() + "_" + UUID.randomUUID();
            fileService.saveFile(imageName, image.getBytes());
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}