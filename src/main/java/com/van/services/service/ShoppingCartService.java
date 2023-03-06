package com.van.services.service;

import com.van.services.exception.BadRequestException;
import com.van.services.model.Item;
import com.van.services.model.ItemDescription;
import com.van.services.model.ItemDto;
import com.van.services.model.ShoppingCart;
import com.van.services.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

import static com.van.services.security.SecurityUtils.getShoppingCartId;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
@AllArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ItemDescriptionService itemDescriptionService;
    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<ShoppingCart> addItem(ItemDto itemDto) {
        log.debug("addItem({})", itemDto);
        return itemDescriptionService.findById(itemDto.getItemId())
                .switchIfEmpty(Mono.error(new BadRequestException(String.format("Item with id %s does not exists!", itemDto.getItemId()))))
                .flatMap(itemDescription -> saveNewVersion(itemDescription, itemDto)
                        .repeatWhenEmpty(30, times -> {
                            log.debug("Repeat save , times : {}", times);
                            return saveNewVersion(itemDescription, itemDto);
                        })
                        .timeout(Duration.ofSeconds(30))
                ).map(shoppingCart -> {
                    log.debug("Saved shopping cart: {}", shoppingCart);
                    return shoppingCart;
                });
    }

    public Mono<Iterable<Item>> getScItems() {
        return getCurrentSc().map(shoppingCart -> {
            log.debug("Current shopping Cart : {}", shoppingCart);
            return shoppingCart.getItems().values();
        });
    }

    private ShoppingCart getEmptyShoppingCart(String id) {
        return ShoppingCart.builder()
                .id(id)
                .version(0L)
                .build();
    }

    private Mono<ShoppingCart> getCurrentSc() {
        return getShoppingCartId()
                .map(this::getEmptyShoppingCart)
                .flatMap(shoppingCartRepository::insert)
                .onErrorResume(e -> {
                    log.debug("Shopping Cart with this ID already exists");
                    return getShoppingCartId().flatMap(shoppingCartRepository::findById);
                });
    }

    private Mono<ShoppingCart> saveNewVersion(ItemDescription itemDescription, ItemDto itemDto) {
        log.debug("saveNewVersion {} , {} ", itemDescription, itemDto);
        return getCurrentSc().flatMap(shoppingCart -> {
            Query query = new Query().addCriteria(
                    where("id").is(shoppingCart.getId())
                            .and("version").is(shoppingCart.getVersion())
            );
            log.debug("Query : {}", query);
            Map<String, Item> map = shoppingCart.getItems();
            Item existItem = map.getOrDefault(itemDto.getItemId(), Item.builder()
                    .title(itemDescription.getTitle())
                    .build());
            log.debug("existItem : {}", existItem);
            existItem.setValue(existItem.getValue() + itemDto.getValue());
            existItem.setTotalPrice(existItem.getValue() * itemDescription.getPrice());
            log.debug("newItem : {}", existItem);
            shoppingCart.setVersion(shoppingCart.getVersion() + 1);
            map.put(itemDto.getItemId(), existItem);
            log.debug("Try to save :{}", shoppingCart);
            return mongoTemplate.findAndReplace(query, shoppingCart, FindAndReplaceOptions.options().returnNew());
        });
    }
}
