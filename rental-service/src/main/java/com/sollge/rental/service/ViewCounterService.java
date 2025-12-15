package com.sollge.rental.service;

import com.sollge.rental.entity.RentalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewCounterService {

    private static final Update INCREMENT_VIEWS = new Update().inc("views", 1);

    private final MongoTemplate mongoTemplate;

    @Async
    public void increment(final @NonNull RentalEntity rentalEntity) {
        var query = new Query(Criteria.where("_id").is(rentalEntity.getId()));
        mongoTemplate.updateFirst(query, INCREMENT_VIEWS, RentalEntity.class);
    }
}