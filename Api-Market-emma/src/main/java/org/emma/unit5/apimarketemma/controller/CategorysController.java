package org.emma.unit5.apimarketemma.controller;

import org.emma.unit5.apimarketemma.model.entities.Category;
import org.emma.unit5.apimarketemma.service.CategorysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorys")
public class CategorysController {

    @Autowired
    private CategorysService categorysService;

    @GetMapping
    public List<Category> getAllCategorys() {
        return categorysService.getAllCategories();
    }

}
