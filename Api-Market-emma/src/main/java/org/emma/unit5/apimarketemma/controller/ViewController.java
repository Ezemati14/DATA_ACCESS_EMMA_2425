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
import org.emma.unit5.apimarketemma.service.SellerProductService;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.emma.unit5.apimarketemma.service.SellersService.encryptPassword;

@Controller
public class ViewController {

    @Autowired
    private ISellersDAO sellersDAO;
    @Autowired
    private IProductsDAO productsDAO;
    @Autowired
    private ISellerProductDAO sellerProductDAO;
    @Autowired
    private SellerProductService sellerProductService;
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
    /** ******************************* PAGINA DE REGISTER ************************************* **/
    //Mostramos la pagina con un objeto vacio al principio
    //Para que el formulario este vacio
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("seller", new Seller());
        return "register";
    }
    //Procesamos el formulario de registro
    //Este metodo maneja metodo POST al endpoint "/register"
    //Llega todos los datos al atributo seller, y se asigna automaticamente
    @PostMapping("/register")
    public String registerSeller(@ModelAttribute Seller seller, RedirectAttributes redirectAttributes) {
        //Buscamos en el DAO (base de datos) que no tengamos un cif repetido
        //Si ya existe uno, se envia un mensaje de error al html
        if(sellersDAO.findByCif(seller.getCif()).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "El CIF ya esta registrado");
            return "redirect:/register";
        }
        //Encriptamos la contraseña y se guarda en la base de datos
        //Obtenemos la contraseña normal en el parametro, y luego la encriptamos
        String encryptedPassword = encryptPassword(seller.getPlainPassword());
        // Cifrar la contraseña antes de guardarla
        //Una vez encriptada, se gurada en el set de Password
        seller.setPassword(encryptedPassword);

        // Guardar en la base de datos
        sellersDAO.save(seller);

        // Mensaje de éxito
        redirectAttributes.addFlashAttribute("successMessage", "Seller registrado con éxito.");
        return "redirect:/register";
    }

    /** ******************************* PAGINA DE WELCOME ************************************* **/

    @GetMapping("/welcome")
    public String welcomePage(Model model, Authentication auth) {
        String cif = auth.getName();

        Seller seller = sellersDAO.findByCif(cif)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        model.addAttribute("seller", seller);
        return "welcome";
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
                String encryptedPassword = encryptPassword(seller.getPlainPassword());
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

    //Controlador para cargar ofertas
    //Se usa para pasar los modelos al html offer.html
    @GetMapping("/offer")
    public String showOfferPage(@RequestParam(value = "productId", required = false) Integer productId, Model model) {
        //Obtenemos el cif del vendedor autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerCif = authentication.getName();

        //Este metodo se encuentra en el sellerProductDAO, que nos devuelve las ofertas
        //que tenga este seller. En el parametro se pasa el cif para obtener las ofertas de ese seller
        //Al final guardamos esta variable en un model, para usarlo en el html
        List<SellerProduct> sellerOffers = sellerProductDAO.findOffersByCif(sellerCif);
        //Nos devuelve una lista de sellerProducts, pasandole por parametro un cif
        List<SellerProduct> sellerProducts = sellerProductDAO.findBySellerCif(sellerCif);
        //Lista para guarda los productos del sellerProducts
        List<Product> products = new ArrayList<>();
        //En el integer ira el id de product, y eb BigDecimal va el precio
        Map<Integer, BigDecimal> productPrices = new HashMap<>();
        Map<Integer, Boolean> productActiveStatus = new HashMap<>();

        //Recorremos tod0 los datos del sellerProduct
        //Para poder obtener todos los productos
        for (SellerProduct sellerProduct : sellerProducts) {
            //Obtenemos el producto del sellerProduct, y lo guardamos en la variable prodcut
            Product product = sellerProduct.getProduct();
            //Ese product lo agregamos a la lista o array de products
            products.add(product);
            //Obtenemos el id de la clase product, y el precio del sellerProduct
            //y lo guardamos en el HasMap productPrices
            productPrices.put(product.getProductId(), sellerProduct.getPrice());
            //Buscamos el id de prodcut, y el active de product
            //y guardamos el estado
            productActiveStatus.put(product.getProductId(), product.getActive());
        }

        SellerProduct selectedProduct = null;
        if (productId != null) {
            Optional<SellerProduct> sellerProductOpt = sellerProductDAO.findById(productId);
            if (sellerProductOpt.isPresent()) {
                selectedProduct = sellerProductOpt.get();
            }
        }

        // ESte products me muestra todos los productos que tiene el seller
        // para mostrarlos en un select/option, para asi selecciona la oferta
        //de ese producto. Se pasa al html offer.html
        model.addAttribute("products", products);
        model.addAttribute("productPrices", productPrices);
        model.addAttribute("productActiveStatus", productActiveStatus);
        model.addAttribute("selectedProduct", selectedProduct);
        //Este model tiene las las filas de las ofertas que tenga el sellerProduct
        //Nos devuel ve una fila
        model.addAttribute("sellerOffers", sellerOffers);

        return "offer";
    }

    //Se usa en la tabla de offer.html
    //Para borrar las ofertas que tenga el seller
    //Este metodo esta dentro de un boton para eliminarlo pasandole el id del sellerProduct
    @GetMapping("/deleteOffer")
    public String deleteOffer(@RequestParam Integer offerId, RedirectAttributes redirectAttributes) {
        //Le llega del html por parametro el id del sellerProduct
        //Lo busca con el findById, que puede ser que no lo encuentre por eso es Optional
        Optional<SellerProduct> sellerProductOpt = sellerProductDAO.findById(offerId);
        //Si el sellerProductOpt esta presente, entra al if
        if (sellerProductOpt.isPresent()) {
            //Obtenemos el sellerProductOpt y lo guardamos en una variable
            //sellerProduct para luego manejar los campos
            SellerProduct sellerProduct = sellerProductOpt.get();

            // Eliminar la oferta (poner los valores en null)
            //Como en el sellerProduct tenemos acceso a todos los campos
            //pasamos a null los campos que nos interesa
            sellerProduct.setOfferPrice(null);
            sellerProduct.setOfferStartDate(null);
            sellerProduct.setOfferEndDate(null);

            // Desde el sellerProducto obtenemos la clase Product
            //y la guardamos en una variable
            Product product = sellerProduct.getProduct();
            //Y el active lo pasamos a false
            product.setActive(Boolean.valueOf(false));
            //Con el save del DAO guardamos esos cambios
            productsDAO.save(product);
            //Y los cambios del precio de la oferta
            //y las fechas, las guardamos tambien en la tabla sellerProduct
            sellerProductDAO.save(sellerProduct);
            //Mensajes de satifaccion si se borro o no la oferta.
            redirectAttributes.addFlashAttribute("successMessage", "Oferta eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Oferta no encontrada.");
        }

        return "redirect:/offer";
    }

    //Se usa en el formulario del html offer.html para crear una ofrerta,
    //Ademas hace la validacion del tipo de oferta segun los dias
    @PostMapping("/create-offer")
    public Object createOffer(
            @RequestParam Integer productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerEndDate,
            @RequestParam(required = false) Boolean updateExisting,
            RedirectAttributes redirectAttributes) {

        //Llega el cif del seller que inicio sesion
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear una oferta.");
        }
        // Obtener el seller autenticado
        //Con la autenticacion, sacamos el cif y lo guardamos en el sellerCif
        String sellerCif = authentication.getName();
        //Ahora que ya tenemos el cif del seller, buscamos en la base de datos
        //los datos de ese seller. Que esta funcion nos devuelve
        //Los datos del seller completo
        Seller seller = sellersDAO.findByCif(sellerCif)
                .orElseThrow(() -> new RuntimeException("Seller no encontrado"));
        //Y que hacemos con esto, como tenemos los datos del seller completo
        //guardamos por variable solamente el id
        //Para luego ese id usarlo para buscar los productos de ese id
        Integer sellerId = seller.getSellerId();
        //Este metodo tiene una query, y se le pasa por parametro el id del producto y seller
        //Y esto nos va a devolver el producto que tenga ese sellerId, y ademas el productId
        SellerProduct sellerProduct = sellerProductDAO.findByProductIdAndSellerId(productId, sellerId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para este vendedor"));


        // Verificar si ya existe una oferta para este producto
        if (sellerProduct.getOfferStartDate() != null && sellerProduct.getOfferEndDate() != null) {
            if (updateExisting == null) {
                redirectAttributes.addFlashAttribute("confirmUpdate", Optional.of(true));
                return "redirect:/offer"; // Preguntar si quiere actualizar
            } else if (!updateExisting) {
                redirectAttributes.addFlashAttribute("errorMessage", "Oferta no actualizada.");
                return "redirect:/offer";
            }
        }
        //Una vez que ya tenemos el id, que queda guardado en el sellerProduct, busacmos el precio
        BigDecimal originalPrice = sellerProduct.getPrice();
        long days = ChronoUnit.DAYS.between(offerStartDate, offerEndDate) + 1;

        // Determinar el porcentaje de descuento
        int discountPercentage;
        if (days == 1) discountPercentage = 50;
        else if (days <= 3) discountPercentage = 30;
        else if (days <= 7) discountPercentage = 20;
        else if (days <= 15) discountPercentage = 15;
        else if (days <= 30) discountPercentage = 30; // 30%
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "El periodo no puede exceder los 30 días.");
            return "redirect:/offer"; }
        // else return ResponseEntity.badRequest().body("El periodo no puede exceder los 30 días.");


        // Aplicar el descuento
        BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100));
        BigDecimal offerPrice = originalPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

        System.out.println("Product ID recibido: " + productId);
        System.out.println("Producto encontrado: " + sellerProduct.getProduct().getProductName());
        System.out.println("Precio original del producto: " + sellerProduct.getPrice());
        System.out.println("Precio calculado en backend: " + offerPrice);

        // Guardar los valores en la BD
        sellerProduct.setOfferStartDate(offerStartDate);
        sellerProduct.setOfferEndDate(offerEndDate);
        sellerProduct.setOfferPrice(offerPrice);
        sellerProductDAO.save(sellerProduct);

        redirectAttributes.addFlashAttribute("successMessage", "Oferta creada con éxito. Precio con descuento: " + sellerProduct.getOfferPrice() +
                                            " del producto : " + sellerProduct.getProduct().getProductName());
        return "redirect:/offer";
        //return ResponseEntity.ok("Oferta creada con éxito. Precio con descuento: " + offerPrice);
    }

    /** ******************************* PAGINA DE PRODUCTOS ************************************* **/

    @GetMapping("/addproduct")
    public String showAddProductPage(Model model) {
        // Nos devuelve todas las categorias en una lista
        List<Category> categories = categorysDAO.findAll();
        //Asinga las lista de categorias a categories, para pasar al html addproduct.html
        model.addAttribute("categories", categories);

        // Llamamos a la funcion para que nos devuelva el cif autenticado
        String cif = getAuthenticatedUserCif();

        // Obtener información del vendedor
        Optional<Seller> seller = sellersDAO.findByCif(cif);
        //A travez del cif, obtenemos todos los datos de ese seller
        seller.ifPresent(s -> model.addAttribute("seller", s));

        // Obtener productos asociados al vendedor (si existen)
        //Esto lo usamos en la tabla de addproduct.html
        //para mostrar una lista de los productos que tiene el seller
        List<SellerProduct> sellerProducts = sellerProductService.getSellerProductsByCif(cif);
        //Devolvemos toda la lista de los productos
        model.addAttribute("sellerProducts", sellerProducts);

        return "addproduct";
    }
    // Métod0 para eliminar un producto del vendedor
    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer productId) {
        // Llamar al servicio para eliminar el producto
        sellerProductService.deleteProductById(productId);
        // Después de eliminar, redirigir a la misma página para ver los productos actualizados
        return "redirect:/addproduct";
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
            @RequestParam Integer productId, // Parámetro que corresponde al campo "productId" en el formulario
            //Aca llega el numero que se pone en el input de stock enlazado con name="stock"
            @RequestParam Integer stock, // Parámetro que corresponde al campo "stock" en el formulario
            @RequestParam BigDecimal price,  // Parámetro que corresponde al campo "price" en el formulario
            RedirectAttributes redirectAttributes) {
        //Stock pase a vale por ejemplo 15 ahora, y se pasa a la funcion del servicio
        productService.addProductToSeller(cif, productId, stock, price);
        Optional<Product> product = productsDAO.findById(productId);

        redirectAttributes.addFlashAttribute("successMessage", "Producto agregado correctamente " + product.get().getProductName());
        return "redirect:/addproduct";
    }

    //aca llega del fetch esto por ejemplo /addproduct/Bebidas/B12345678.
    @GetMapping("/addproduct/{category}/{cif}")
    @ResponseBody
    //Aca se guarda la Bebidas en category , y el B12345678 en el cif
    public List<Product> getMissingProducts(@PathVariable String category, @PathVariable String cif) {
        //aca recibe esos 2 parametros bebidas y B12345678
        return productService.getMissingProducts(category, cif);
    }

    /** ******************************* PAGINA DE PRODUCTOS ************************************* **/

    @GetMapping("/createProduct")
    public String showCreateProductPage(Model model) {
        //Obtenemos todas las categorias con findAll
        List<Category> categories = categorysDAO.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "createProduct"; // Renderiza el HTML
    }

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        //Los datos que se pasan por el input, se guardan en el product del parametro
        //y luego con la funcion del save, guardamos ese producto en la base de datos
        //que esta asociada con el DAO de products
        productsDAO.save(product);

        // Mensaje de éxito
        redirectAttributes.addFlashAttribute("successMessage", "Producto creado con éxito.");
        return "redirect:/createProduct";
    }
}


