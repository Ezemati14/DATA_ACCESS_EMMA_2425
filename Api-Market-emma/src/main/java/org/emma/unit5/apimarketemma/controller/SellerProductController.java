package org.emma.unit5.apimarketemma.controller;

import org.emma.unit5.apimarketemma.model.dto.SellerProductDTO;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.emma.unit5.apimarketemma.service.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/seller-products")
public class SellerProductController {

    @Autowired
    private SellerProductService sellerProductService;

    @GetMapping
    public List<SellerProduct> getAllSellerProducts() {
       return sellerProductService.getAllSellerProducts();
    }

    @GetMapping("/offer-date")
    public List<BigDecimal> getOfferDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return sellerProductService.getSellerNamesByOfferDates(startDate, endDate);
    }

   /** @GetMapping
    public List<SellerProductDTO> getAllSellerProducts() {
        return sellerProductService.getAllSellerProducts();
    }

    @GetMapping("/seller/{sellerName}")
    public List<SellerProductDTO> getSellerProductsBySeller(@PathVariable String sellerName) {
        return sellerProductService.getSellerProductsBySellerName(sellerName);
    } **/
}
