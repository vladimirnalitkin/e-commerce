package com.van.services.repository;

import com.van.services.model.ItemDescription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ItemDescriptionRepository extends ReactiveMongoRepository<ItemDescription, String> {
  Flux<ItemDescription> findByTitleContaining(String title);
}
