package org.emma.unit5.apimarketemma.service;

import org.emma.unit5.apimarketemma.model.dao.IProductsDAO;
import org.emma.unit5.apimarketemma.model.dto.ProductDTO;
import org.emma.unit5.apimarketemma.model.entities.Category;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private IProductsDAO productsDAO;

    //Obtenemos todos los nombres de la tabla productos, usando una lista de string
    public List<String> getAllProductsNames() {
       return  productsDAO.findAllProductNames();

      /** Mapea productDTO y mostramos sus campos
       * return products.stream()
               .map(ProductDTO::new)
               .collect(Collectors.toList()); **/
    }

   /** public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        List<Product> products = productsDAO.findByCategory_CategoryName(categoryName);
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    } **/
}
