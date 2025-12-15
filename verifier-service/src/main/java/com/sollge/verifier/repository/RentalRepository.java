package com.sollge.verifier.repository;

import com.sollge.verifier.entity.RentalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RentalRepository extends MongoRepository<RentalEntity, UUID> {
}
