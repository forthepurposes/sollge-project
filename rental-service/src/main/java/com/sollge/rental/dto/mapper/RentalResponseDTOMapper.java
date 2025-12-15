package com.sollge.rental.dto.mapper;

import com.sollge.rental.dto.RentalResponseDTO;
import com.sollge.rental.entity.RentalEntity;
import com.sollge.rental.util.ImageUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Component
public class RentalResponseDTOMapper implements Function<RentalEntity, RentalResponseDTO> {

    @Override
    public @NonNull RentalResponseDTO apply(final @NonNull RentalEntity entity) {
        String[] imagesLinks = Optional.ofNullable(entity.getImagesNames())
                .map(this::convertToImagesLinks)
                .filter(images -> images.length != 0)
                .orElse(new String[] { ImageUtil.getDefaultImagePath() });

        return RentalResponseDTO.builder()
                .id(entity.getId())
                .ownerId(entity.getOwnerId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imagesLinks(imagesLinks)
                .price(entity.getPrice())
                .views(entity.getViews())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private @NonNull String[] convertToImagesLinks(final @NonNull String[] imagesNames) {
        return Arrays.stream(imagesNames)
                .map(ImageUtil::getImageURL)
                .toArray(String[]::new);
    }
}
