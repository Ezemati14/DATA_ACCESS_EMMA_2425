package org.emma.unit5.apimarketemma.service;

import org.emma.unit5.apimarketemma.model.dao.IProductsDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellerProductDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.ProductDTO;
import org.emma.unit5.apimarketemma.model.entities.Category;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private IProductsDAO productsDAO;
    @Autowired
    private ISellersDAO sellersDAO;
    @Autowired
    private ISellerProductDAO sellerProductsDAO;
    //Llamamos a la funcion del DAO, y le pasamos por paramtros en nombre de categoria y cif
    //Esta funcion luego se llama de el ViewController
    public List<Product> getMissingProducts(String categoryName, String sellerCif) {
        //Aca llega bebidas y B12345678
        return productsDAO.findMissingProductsBySellerAndCategory(sellerCif, categoryName);
    }

    //Si llego agregar un campo nuevo, no haria falta de agregar nada, ya que
    //en el metodo de arriba, maneja ese campo

    //Stock pasa a valer 15, que fue lo que puso el usuario en el input
    public void addProductToSeller(String sellerCif, Integer productId, Integer stock, BigDecimal price) {
        //Busca al vendedor seller
        Seller seller = sellersDAO.findByCif(sellerCif)
                .orElseThrow(() -> new RuntimeException("Seller no encontrado"));
        //Busca al producto con el productId
        Product product = productsDAO.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        SellerProduct sellerProduct = new SellerProduct();
        sellerProduct.setSeller(seller);
        sellerProduct.setProduct(product);
        //stock que vale 15, con el set se establece ese 15, en el campo del sellerProduct stock
        sellerProduct.setStock(stock);
        sellerProduct.setPrice(price);
        //Y finaliza que con el save, se guarda los datos en la base de datos
        sellerProductsDAO.save(sellerProduct);
    }
    public List<String> getAllProductsNames() {
       return  productsDAO.findAllProductNames();
    }
}
