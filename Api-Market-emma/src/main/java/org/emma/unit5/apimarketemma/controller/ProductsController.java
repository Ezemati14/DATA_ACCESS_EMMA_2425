package org.emma.unit5.apimarketemma.controller;

import org.emma.unit5.apimarketemma.model.dto.ProductDTO;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<String>> getAllProducts() {
        List<String> listProducts = productsService.getAllProductsNames();
        return ResponseEntity.ok(listProducts);
    }

   /** @GetMapping("/category/{categoryName}")
    public List<ProductDTO> getAllProductsByCategory(@PathVariable String categoryName) {
        return productsService.getProductsByCategoryName(categoryName);
    } **/
}
