package com.van.services.service;

import com.van.services.model.ItemDescription;
import com.van.services.repository.ItemDescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ItemDescriptionService {
  private final ItemDescriptionRepository repository;

  public Flux<ItemDescription> findAll() {
    return repository.findAll();
  }

  public Flux<ItemDescription> findByTitleContaining(String title) {
    return repository.findByTitleContaining(title);
  }

  public Mono<ItemDescription> findById(String id) {
    return repository.findById(id);
  }

  public Mono<ItemDescription> save(ItemDescription item) {
    return repository.save(item);
  }

}
