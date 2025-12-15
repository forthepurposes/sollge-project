package com.sollge.rental.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@UtilityClass
public class ImageUtil {

    private final long MAX_FILE_SIZE = 1_000_000; // 1 MB

    private final String CDN_IMAGE_PATH = "https://sollgeblob.z1.web.core.windows.net/";
    private final String DEFAULT_IMAGE_PATH = CDN_IMAGE_PATH + "no_image_v1.png";

    public @NonNull String getImageURL(final @Nullable String imageName) {
        if (StringUtils.isEmpty(imageName)) {
            return DEFAULT_IMAGE_PATH;
        }

        return CDN_IMAGE_PATH + imageName;
    }

    public @NonNull String getDefaultImagePath() {
        return DEFAULT_IMAGE_PATH;
    }

    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }
}