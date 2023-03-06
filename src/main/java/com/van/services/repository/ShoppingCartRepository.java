package com.van.services.repository;

import com.van.services.model.ShoppingCart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends ReactiveMongoRepository<ShoppingCart, String> {
}
