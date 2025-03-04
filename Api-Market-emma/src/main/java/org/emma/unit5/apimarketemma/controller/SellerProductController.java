package org.emma.unit5.apimarketemma.controller;

import org.emma.unit5.apimarketemma.model.dto.SellerProductDTO;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.emma.unit5.apimarketemma.service.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seller-products")
public class SellerProductController {

    @Autowired
    private SellerProductService sellerProductService;

    @GetMapping
    public List<SellerProduct> getAllSellerProducts() {
       return sellerProductService.getAllSellerProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerProduct> getSellerProductById(@PathVariable Integer id) {
        Optional<SellerProduct> product = sellerProductService.getSellerProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-cif/{cif}")
    public ResponseEntity<List<SellerProduct>> getSellerProductsByCif(@PathVariable String cif) {
        List<SellerProduct> products = sellerProductService.getSellerProductsByCif(cif);
        return ResponseEntity.ok(products);
    }

    /** 4️⃣ Buscar ofertas activas por CIF */
    @GetMapping("/offers/{cif}")
    public ResponseEntity<List<SellerProduct>> getOffersByCif(@PathVariable String cif) {
        List<SellerProduct> offers = sellerProductService.getOffersByCif(cif);
        return ResponseEntity.ok(offers);
    }

    /** 6️⃣ Crear un nuevo producto */
    @PostMapping
    public ResponseEntity<SellerProduct> createSellerProduct(@RequestBody SellerProduct sellerProduct) {
        SellerProduct savedProduct = sellerProductService.saveSellerProduct(sellerProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /** 7️⃣ Actualizar un producto */
    @PutMapping("/{id}")
    public ResponseEntity<SellerProduct> updateSellerProduct(@PathVariable Integer id, @RequestBody SellerProduct updatedProduct) {
        Optional<SellerProduct> updated = sellerProductService.updateSellerProduct(id, updatedProduct);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** 8️⃣ Eliminar un producto por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellerProduct(@PathVariable Integer id) {
        sellerProductService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    /** 9️⃣ Buscar un producto por productId y sellerId */
    @GetMapping("/by-product-seller")
    public ResponseEntity<SellerProduct> getByProductAndSeller(
            @RequestParam("productId") Integer productId,
            @RequestParam("sellerId") Integer sellerId) {
        Optional<SellerProduct> product = sellerProductService.getByProductIdAndSellerId(productId, sellerId);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/offer-date")
    public List<BigDecimal> getOfferDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return sellerProductService.getSellerNamesByOfferDates(startDate, endDate);
    }

}
