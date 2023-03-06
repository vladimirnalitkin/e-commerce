package com.van.services.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.services.exception.BadRequestException;
import com.van.services.model.Item;
import com.van.services.model.ItemDto;
import com.van.services.model.ShoppingCart;
import com.van.services.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.van.services.constant.AppConstants.SHOPPING_CART_URL;

@RestController
@RequestMapping(SHOPPING_CART_URL)
@AllArgsConstructor
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ObjectMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Iterable<Item>> getItems() {
        return shoppingCartService.getScItems();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ShoppingCart> addNewItemToCart(@RequestBody String item) {
        log.debug("Put item : {}", item);
        try {
            ItemDto itemDto = mapper.readValue(item, ItemDto.class);
            if (Objects.isNull(itemDto) || Strings.isBlank(itemDto.getItemId())) {
                throw new BadRequestException("Can't insert empty item to Shopping cart!");
            }
            return shoppingCartService.addItem(itemDto);
            //        .switchIfEmpty(Mono.error(new ShoppingCartWasModifiedException("Shopping cart was modified in other session. Please refresh shopping cart")));
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Can't insert empty item to Shopping cart!");
        }
    }
}
