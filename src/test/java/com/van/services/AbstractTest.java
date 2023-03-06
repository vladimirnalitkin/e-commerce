package com.van.services;

import com.van.services.model.ItemDescription;
import com.van.services.repository.ItemDescriptionRepository;
import com.van.services.repository.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static com.van.services.TestHelper.*;
import static com.van.services.TestHelper.TEST_ITEM_TITLE2;

@Slf4j
public abstract class AbstractTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ItemDescriptionRepository itemDescriptionRepository;

    @BeforeEach
    public void beforeEach() {
        log.debug("beforeEach run");
        itemDescriptionRepository.deleteAll().block();
        shoppingCartRepository.deleteAll().block();
        itemDescriptionRepository.save(ItemDescription.builder().id(TEST_ITEM_ID).title(TEST_ITEM_TITLE).price(34.5).build()).block();
        itemDescriptionRepository.save(ItemDescription.builder().id(TEST_ITEM_ID2).title(TEST_ITEM_TITLE2).price(7.5).build()).block();
    }

}
