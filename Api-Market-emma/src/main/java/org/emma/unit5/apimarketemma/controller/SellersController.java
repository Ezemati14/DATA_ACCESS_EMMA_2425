package org.emma.unit5.apimarketemma.controller;

import jakarta.validation.Valid;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.SellerDTO;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.service.SellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sellers")
public class SellersController {

    @Autowired
    private SellersService sellersService;

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellersService.getAllSellers();
    }

    @GetMapping("/{cif}")
    public ResponseEntity<Seller> getSellerByCIF(@PathVariable("cif") String cif) {
        Seller seller = sellersService.getSellerByCif(cif);

        if(seller == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(seller);
    }

    @PostMapping("seller/{id}")
    public ResponseEntity<String> UpdateSeller(@Validated @PathVariable Integer id,
                                               @RequestBody  SellerDTO sellerDTO) {
       sellersService.updateSeller(id, sellerDTO);;
       return ResponseEntity.ok("Seller updated successfully!");
    }

    @PostMapping("/addseller")
    public ResponseEntity<String> addSeller(@Valid @RequestBody SellerDTO sellerDTO) {
        try{
            Seller newSeller = sellersService.addSeller(sellerDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Seller created with ID: " + newSeller.getSellerId());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());
        }
    }

    @DeleteMapping("/seller/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Integer id) {
        boolean deleted = sellersService.deleteSeller(id);
        if(deleted) {
            return ResponseEntity.ok("The seller was deleted! " + id);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
