package io.spring.cloud.samples.commerce.ui.controllers;

import io.spring.cloud.samples.commerce.ui.services.commerce.Commerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.cloud.samples.commerce.ui.services.commerce.CommerceService;


@RestController
public class UiController {

    @Autowired
    CommerceService service;

    @RequestMapping("/items")
    public Commerce[] items() {
        return service.findAll();
    }

    @RequestMapping("/category/{cat}")
    public Commerce[] itemsByCategory(@PathVariable("cat") String category) {
        return service.findByCategory(category);
    }

    @RequestMapping("/item/{itemid}")
    public Commerce[] itemById(@PathVariable("itemid") Long id){
        return service.findById(id);
    }


}
