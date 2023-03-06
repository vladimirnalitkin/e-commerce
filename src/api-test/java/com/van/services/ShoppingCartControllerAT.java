package com.van.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.services.model.ItemDto;
import com.van.services.model.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static com.van.services.TestHelper.TEST_ITEM_ID;
import static com.van.services.TestHelper.TEST_TOKEN;
import static com.van.services.constant.AppConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ShoppingCartControllerAT {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test_add_item_ok() throws Exception {
        ItemDto itemDto = ItemDto.builder()
                .itemId(TEST_ITEM_ID)
                .value(2.0)
                .build();

        ShoppingCart shoppingCart = given()
                .log().all()
                .header("Content-type", "application/json")
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + TEST_TOKEN)
                .and()
                .body(mapper.writeValueAsString(itemDto))
                .when()
                .post("http://localhost:8081/" + SHOPPING_CART_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ShoppingCart.class);

        assertNotNull(shoppingCart);
        assertFalse(shoppingCart.getItems().isEmpty());
    }
}
