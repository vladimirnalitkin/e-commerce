package com.van.services.service;

import com.van.services.AbstractTest;
import com.van.services.model.Item;
import com.van.services.model.ItemDescription;
import com.van.services.model.ItemDto;
import com.van.services.model.ShoppingCart;
import com.van.services.repository.ItemDescriptionRepository;
import com.van.services.repository.ShoppingCartRepository;
import com.van.services.security.jwt.WithOrgMockUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Iterator;
import java.util.Map;

import static com.van.services.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithOrgMockUser(shoppingCartId = TEST_ID)
@Slf4j
class ShoppingCartServiceTest extends AbstractTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ItemDescriptionRepository itemDescriptionRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void givenShoppingCart_whenGetScItems_thenSuccess() {
        shoppingCartRepository.save(ShoppingCart.builder().id(TEST_ID).version(1L).items(Map.of(TEST_ITEM_ID, TEST_ITEM_VAL)).build()).block();
        StepVerifier
                .create(shoppingCartService.getScItems())
                .assertNext(items -> {
                    assertNotNull(items);
                    Iterator<Item> iterator = items.iterator();
                    assertTrue(iterator.hasNext());
                    assertEquals(TEST_ITEM_VAL, iterator.next());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenShoppingCart_whenAddItem_thenSuccess() {
        shoppingCartService.addItem(ItemDto.builder().itemId(TEST_ITEM_ID).value(TEST_ITEM_VAL.getValue()).build()).block();
        log.debug("Save ok");
        Iterable<Item> items = shoppingCartService.getScItems().block();
        assert items != null;
        log.debug(items.toString());
    }

    @Test
    public void givenShoppingCart_whenAddItem2_thenSuccess() {
        ItemDto newItemDto = ItemDto.builder().itemId(TEST_ITEM_ID).value(3.0).build();
        StepVerifier
                .create(shoppingCartService.addItem(newItemDto))
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }
}