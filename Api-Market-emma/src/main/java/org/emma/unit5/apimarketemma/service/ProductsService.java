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


    public List<Product> getMissingProducts(String categoryName, String sellerCif) {
        return productsDAO.findMissingProductsBySellerAndCategory(sellerCif, categoryName);
    }

    public void addProductToSeller(String sellerCif, Integer productId, Integer stock, BigDecimal price) {

        Seller seller = sellersDAO.findByCif(sellerCif)
                .orElseThrow(() -> new RuntimeException("Seller no encontrado"));
        Product product = productsDAO.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        SellerProduct sellerProduct = new SellerProduct();
        sellerProduct.setSeller(seller);
        sellerProduct.setProduct(product);
        sellerProduct.setStock(stock);
        sellerProduct.setPrice(price);

        sellerProductsDAO.save(sellerProduct);
    }
    public List<String> getAllProductsNames() {
       return  productsDAO.findAllProductNames();
    }
}
