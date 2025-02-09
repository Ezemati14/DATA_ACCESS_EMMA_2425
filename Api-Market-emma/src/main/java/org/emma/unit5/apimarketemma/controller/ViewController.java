package org.emma.unit5.apimarketemma.controller;

import jakarta.servlet.http.HttpSession;
import org.emma.unit5.apimarketemma.model.dao.ICategorysDAO;
import org.emma.unit5.apimarketemma.model.dao.IProductsDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellerProductDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.entities.Category;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.emma.unit5.apimarketemma.service.ProductsService;
import org.emma.unit5.apimarketemma.service.SellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class ViewController {

    @Autowired
    private ISellersDAO sellersDAO;
    @Autowired
    private IProductsDAO productsDAO;
    @Autowired
    private ISellerProductDAO sellerProductDAO;
    @Autowired
    private ICategorysDAO categorysDAO;
    @Autowired
    private ProductsService productService;

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
            return "redirect:/welcome";
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
        //Obtenemos el cif del vendedor autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerCif = authentication.getName();

        List<SellerProduct> sellerProducts = sellerProductDAO.findBySellerCif(sellerCif);
        //Nos devuelve los productos que el vendedor tiene disponible con la consulta creada en el sellerProductDAO
        //List<SellerProduct> sellerProducts1 = sellerProductDAO.findProductsBySellerCif(sellerCif);

        List<Product> products = new ArrayList<>();
        Map<Integer, BigDecimal> productPrices = new HashMap<>();

        for (SellerProduct sellerProduct : sellerProducts) {
            Product product = sellerProduct.getProduct();
            products.add(product);
            productPrices.put(product.getProductId(), sellerProduct.getPrice());
        }

        // Pasar los datos al modelo
        model.addAttribute("products", products);
        model.addAttribute("productPrices", productPrices);

        return "offer";
    }

    @PostMapping("/create-offer")
    public Object createOffer(
            @RequestParam Integer productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerEndDate, RedirectAttributes redirectAttributes) {

        SellerProduct sellerProduct = sellerProductDAO.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear una oferta.");
        }

        boolean conflict = sellerProductDAO.existsByProductIdAndOfferPeriod(productId, offerStartDate, offerEndDate);
        if (conflict) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ya existe una oferta para este producto en el periodo seleccionado.");
            return "redirect:/offer";
            //return ResponseEntity.badRequest().body("Ya existe una oferta para este producto en el periodo seleccionado.");
        }

        BigDecimal originalPrice = sellerProduct.getPrice();
        long days = ChronoUnit.DAYS.between(offerStartDate, offerEndDate) + 1;

        // Determinar el porcentaje de descuento
        BigDecimal discountPercentage;
        if (days == 1) discountPercentage = BigDecimal.valueOf(0.50);  // 50%
        else if (days <= 3) discountPercentage = BigDecimal.valueOf(0.30); // 30%
        else if (days <= 7) discountPercentage = BigDecimal.valueOf(0.20); // 20%
        else if (days <= 15) discountPercentage = BigDecimal.valueOf(0.15); // 15%
        else if (days <= 30) discountPercentage = BigDecimal.valueOf(0.10); // 10%
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "El periodo no puede exceder los 30 días.");
            return "redirect:/offer"; }
        // else return ResponseEntity.badRequest().body("El periodo no puede exceder los 30 días.");


        // Aplicar el descuento
        BigDecimal offerPrice = originalPrice.multiply(BigDecimal.ONE.subtract(discountPercentage));

        // Guardar los valores en la BD
        sellerProduct.setOfferStartDate(offerStartDate);
        sellerProduct.setOfferEndDate(offerEndDate);
        sellerProduct.setOfferPrice(offerPrice);

        sellerProductDAO.save(sellerProduct);
        redirectAttributes.addFlashAttribute("successMessage", "Oferta creada con éxito. Precio con descuento: " + offerPrice);
        return "redirect:/offer";
        //return ResponseEntity.ok("Oferta creada con éxito. Precio con descuento: " + offerPrice);
    }

    /** ******************************* PAGINA DE PRODUCTOS ************************************* **/

    @GetMapping("/addproduct")
    public String showAddProductPage(Model model) {
        // Obtener todas las categorías
        List<Category> categories = categorysDAO.findAll();
        model.addAttribute("categories", categories);

        String cif = getAuthenticatedUserCif();
        Optional<Seller> seller = sellersDAO.findByCif(cif);
        seller.ifPresent(s -> model.addAttribute("seller", s));

        return "addproduct";
    }
    private String getAuthenticatedUserCif() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return authentication.getName();
        }
        return null;
    }

    @PostMapping("/addproduct/save")
    public String saveSellerProduct(
            @RequestParam String cif,
            @RequestParam Integer productId,
            @RequestParam Integer stock,
            @RequestParam BigDecimal price,
            RedirectAttributes redirectAttributes) {

        productService.addProductToSeller(cif, productId, stock, price);
        redirectAttributes.addFlashAttribute("successMessage", "Producto agregado correctamente");
        return "redirect:/addproduct";
    }

    @GetMapping("/addproduct/{category}/{cif}")
    @ResponseBody
    public List<Product> getMissingProducts(@PathVariable String category, @PathVariable String cif) {
        return productService.getMissingProducts(category, cif);
    }
}




