package org.emma.unit5.apimarketemma.controller;

import org.emma.unit5.apimarketemma.model.dto.SellerProductDTO;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.emma.unit5.apimarketemma.service.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller-products")
public class SellerProductController {

    @Autowired
    private SellerProductService sellerProductService;

    @GetMapping
    public List<SellerProductDTO> getAllSellerProducts() {
        return sellerProductService.getAllSellerProducts();
    }

    @GetMapping("/seller/{sellerName}")
    public List<SellerProductDTO> getSellerProductsBySeller(@PathVariable String sellerName) {
        return sellerProductService.getSellerProductsBySellerName(sellerName);
    }
}
