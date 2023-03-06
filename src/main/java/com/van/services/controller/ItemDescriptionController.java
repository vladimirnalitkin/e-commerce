package com.van.services.controller;

import com.van.services.model.ItemDescription;
import com.van.services.service.ItemDescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.van.services.constant.AppConstants.ITEM_URL;

@RestController
@AllArgsConstructor
@RequestMapping(ITEM_URL)
public class ItemDescriptionController {

    private final ItemDescriptionService itemDescriptionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ItemDescription> getAllItemDescriptions() {
        return itemDescriptionService.findAll();
    }
}
