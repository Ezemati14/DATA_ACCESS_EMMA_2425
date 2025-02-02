package org.emma.unit5.apimarketemma.controller;

import jakarta.servlet.http.HttpSession;
import org.emma.unit5.apimarketemma.model.dao.IProductsDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellerProductDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.emma.unit5.apimarketemma.service.SellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ISellersDAO sellersDAO;
    @Autowired
    private IProductsDAO productsDAO;
    @Autowired
    private ISellerProductDAO sellerProductDAO;

    /** ******************************* PAGINA DE LOGIN ************************************* **/

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cif, @RequestParam String plainPassword, HttpSession session, Model model) {
        try {
            Optional<Seller> seller = sellersDAO.findByCifAndPlainPassword(cif, plainPassword);
            session.setAttribute("seller", seller.get());

            model.addAttribute("sellerCif", seller.get().getCif());
            return "redirect:/welcome";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid CIF or password.");
            return "login";
        }
    }

    /** ******************************* PAGINA DE WELCOME ************************************* **/

    @GetMapping("/welcome")
    public String welcomePage(Model model, Authentication auth) {
        String cif = auth.getName();

        Seller seller = sellersDAO.findByCif(cif)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        model.addAttribute("seller", seller);
        return "welcome";
        /**
         * Seller principal
         *Optional<Seller> seller = sellersDAO.findByCif(principal.getCif());
        model.addAttribute("seller", seller);
        return "welcome";**/
    }

    @PostMapping("/welcome")
    public String updateSeller(@ModelAttribute Seller seller, RedirectAttributes redirectAttributes) {

        Optional<Seller> existingSellerOpt = sellersDAO.findByCif(seller.getCif());

        if (existingSellerOpt.isPresent()) {
            Seller existingSeller = existingSellerOpt.get();

            // Actualiza solo los campos permitidos
            existingSeller.setName(seller.getName());
            existingSeller.setBusinessName(seller.getBusinessName());
            existingSeller.setPhone(seller.getPhone());
            existingSeller.setEmail(seller.getEmail());
            existingSeller.setUrl(seller.getUrl());
            existingSeller.setPro(seller.getPro());

            // Verifica si se proporcionó una nueva contraseña
            if (seller.getPlainPassword() != null && !seller.getPlainPassword().isEmpty()) {
                String encryptedPassword = SellersService.encryptPassword(seller.getPlainPassword());
                existingSeller.setPassword(encryptedPassword);
                existingSeller.setPlainPassword(seller.getPlainPassword());
            }
            sellersDAO.save(existingSeller); // Guarda los cambios
            redirectAttributes.addFlashAttribute("dateMessage", "Datos actualizados correctamente.");
        }
        return "welcome";
    }

    /** ******************************* PAGINA DE OFFER ************************************* **/

    @GetMapping("/offe")
    public String offerPage() {
        return "offe";
    }

    @GetMapping("/offer")
    public String showOfferPage(Model model) {
        List<Product> products = (List<Product>) productsDAO.findAll();
        model.addAttribute("products", products);
        return "offer";
    }

    @PostMapping("/create-offer")
    public ResponseEntity<?> createOffer(@RequestParam Integer productId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerStartDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerEndDate,
                                         @RequestParam BigDecimal offerPrice) {
        // Validar que no haya otra oferta en el mismo periodo
        boolean conflict = sellerProductDAO.existsByProductIdAndOfferPeriod(productId, offerStartDate, offerEndDate);
        if (conflict) {
            return ResponseEntity.badRequest().body("Ya existe una oferta para este producto en el periodo seleccionado.");
        }

        // Validar descuentos
        long days = ChronoUnit.DAYS.between(offerStartDate, offerEndDate) + 1;
        BigDecimal originalPrice = sellerProductDAO.findById(productId).orElseThrow().getPrice();
        BigDecimal maxDiscount;
        if (days == 1) maxDiscount = originalPrice.multiply(BigDecimal.valueOf(0.5));
        else if (days <= 3) maxDiscount = originalPrice.multiply(BigDecimal.valueOf(0.3));
        else if (days <= 7) maxDiscount = originalPrice.multiply(BigDecimal.valueOf(0.2));
        else if (days <= 15) maxDiscount = originalPrice.multiply(BigDecimal.valueOf(0.15));
        else if (days <= 30) maxDiscount = originalPrice.multiply(BigDecimal.valueOf(0.1));
        else {
            return ResponseEntity.badRequest().body("El periodo no puede exceder los 30 días.");
        }

        if (offerPrice.compareTo(originalPrice.subtract(maxDiscount)) < 0) {
            return ResponseEntity.badRequest().body("El precio de oferta excede el descuento permitido.");
        }

        // Crear la oferta
        SellerProduct sellerProduct = new SellerProduct();
        sellerProduct.setProduct(sellerProductDAO.findById(productId).orElseThrow().getProduct());
        sellerProduct.setOfferStartDate(offerStartDate);
        sellerProduct.setOfferEndDate(offerEndDate);
        sellerProduct.setOfferPrice(offerPrice);
        sellerProductDAO.save(sellerProduct);

        return ResponseEntity.ok("Oferta creada con éxito.");
    }
}
