package com.sollge.rental.repository;

import com.sollge.rental.entity.RentalEntity;
import com.sollge.rental.entity.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RentalRepository extends MongoRepository<RentalEntity, UUID> {

    Page<RentalEntity> findAllByStatus(RentalStatus status, Pageable pageable);
    long countByOwnerIdEquals(Long ownerId);
}
