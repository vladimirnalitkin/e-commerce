package com.van.services.controller;

import com.van.services.AbstractTest;
import com.van.services.model.ItemDescription;
import com.van.services.repository.ItemDescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.van.services.TestHelper.TEST_TOKEN;
import static com.van.services.constant.AppConstants.*;

@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemDescriptionControllerTest extends AbstractTest {
    @Autowired
    private ItemDescriptionRepository itemDescriptionRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetItemDescription_thenSuccess() {
        webTestClient
                .get()
                .uri(ITEM_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + TEST_TOKEN)
                .exchange()
                .expectStatus()
                .isOk();
    }
}