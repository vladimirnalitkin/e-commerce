package com.van.services.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.services.AbstractTest;
import com.van.services.model.ItemDto;
import com.van.services.model.ShoppingCart;
import com.van.services.repository.ItemDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.van.services.TestHelper.*;
import static com.van.services.constant.AppConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ShoppingCartControllerTest extends AbstractTest {
    @Autowired
    private ItemDescriptionRepository itemDescriptionRepository;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    ObjectMapper mapper;

    @Test
    void addNewItemToCart() throws JsonProcessingException {
        ItemDto itemDto = ItemDto.builder()
                .itemId(TEST_ITEM_ID)
                .value(TEST_VAL)
                .build();

        ShoppingCart shoppingCart = webTestClient
                .put()
                .uri(SHOPPING_CART_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + TEST_TOKEN)
                .bodyValue(mapper.writeValueAsString(itemDto))
                .exchange()
                .returnResult(ShoppingCart.class)
                .getResponseBody().blockFirst();

        assertNotNull(shoppingCart);
        log.debug(shoppingCart.toString());
        assertEquals(TEST_VAL, shoppingCart.getItems().get(TEST_ITEM_ID).getValue());
    }

    @Test
    void getItems() {
        webTestClient
                .get()
                .uri(SHOPPING_CART_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + TEST_TOKEN)
                .exchange()
                .expectStatus()
                .isOk();
    }

}