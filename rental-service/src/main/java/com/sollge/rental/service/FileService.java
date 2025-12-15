package com.sollge.rental.service;

import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final BlobContainerClient client;

    /**
     * Uploads a file to the S3 bucket.
     *
     * @param keyName Unique identifier of the file
     * @param fileBytes Array of bytes representing the file
     */
    public void saveFile(final @NonNull String keyName, final @NonNull byte[] fileBytes) {
        var dataStream = new ByteArrayInputStream(fileBytes);
        client.getBlobClient(keyName).upload(dataStream, fileBytes.length, true);
    }

    /**
     * Downloads a file from the S3 bucket
     *
     * @param keyName Unique identifier of the file
     * @return Array of bytes representing the file
     */
    public @NonNull byte[] getFile(final @NonNull String keyName) {
        return client.getBlobClient(keyName).downloadContent().toBytes();
    }
}