package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface IProductsDAO extends CrudRepository<Product, Integer> {

     List<Product> findByCategory_CategoryName(String categoryName);

     @Query("SELECT p.productName FROM Product p")
     List<String> findAllProductNames();
}
