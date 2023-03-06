package com.van.services.repository;

import com.van.services.AbstractTest;
import com.van.services.model.Item;
import com.van.services.model.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static com.van.services.TestHelper.TEST_ID;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ItemRepositoryTest extends AbstractTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Test
    public void givenShoppingCart_whenFindAllById_thenSuccess() {
        shoppingCartRepository.save(ShoppingCart.builder().id(TEST_ID).version(1L).build()).block();
        Mono<ShoppingCart> shoppingCartRepositoryById = shoppingCartRepository.findById(TEST_ID);

        StepVerifier
                .create(shoppingCartRepositoryById)
                .assertNext(shoppingCart -> {
                    assertNotNull(shoppingCart.getId());
                    assertEquals(TEST_ID, shoppingCart.getId());
                    assertEquals(1L, shoppingCart.getVersion());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenShoppingCart_whenInsertDublicate_thenError() {
        ShoppingCart shoppingCart = ShoppingCart.builder().id(TEST_ID).version(1L).build();
        shoppingCartRepository.insert(shoppingCart).block();
        assertThrows(org.springframework.dao.DuplicateKeyException.class,
                () -> shoppingCartRepository.insert(shoppingCart).block());
    }

    @Test
    public void givenShoppingCart_whenUpdate_thenSuccess() {
        ShoppingCart shoppingCart = ShoppingCart.builder().id(TEST_ID).version(1L).build();
        log.debug("givenShoppingCart_whenUpdate_thenSuccess try to insert");
        shoppingCartRepository.insert(shoppingCart).block();

        Query query = new Query().addCriteria(
                where("id").is(shoppingCart.getId())
                        .and("version").is(shoppingCart.getVersion())
        );

        log.debug("Query : {}", query);
        shoppingCart.setVersion(shoppingCart.getVersion() + 1);
        log.debug("Try to save :{}", shoppingCart);
        ShoppingCart shoppingCart1 = mongoTemplate.findAndReplace(query, shoppingCart, FindAndReplaceOptions.options().returnNew()).block();
        assertNotNull(shoppingCart1);
        assertEquals(2L, shoppingCart1.getVersion());
    }
}