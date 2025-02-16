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

    @GetMapping("/search")
    public ResponseEntity<Seller> getSellerByCifAndPassword(@RequestParam String cif, @RequestParam String plainPassword) {
        //Pasamos por parametro el cif y el password
        //Llamamos a la funcion del seller services
        Seller seller = sellersService.findByCifAndPlainPassword(cif, plainPassword);
        //Si es diferente de null, devuelve el seller
        if(seller != null) {
            return ResponseEntity.ok(seller);
        }
        return ResponseEntity.notFound().build();
    }

    //Actualizar seller mediante un cif
    @PutMapping("seller/{cif}")
    public ResponseEntity<String> UpdateSeller(@Validated @PathVariable String cif,
                                               @RequestBody  SellerDTO sellerDTO) {
       sellersService.updateSeller(cif, sellerDTO);;
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
}
