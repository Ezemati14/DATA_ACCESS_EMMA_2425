package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductsDAO extends CrudRepository<Product, Integer> {

     @Query("SELECT p.productName FROM Product p")
     List<String> findAllProductNames();

     //El que se usa
     @Query(nativeQuery = true)
     List<Product> findMissingProductsBySellerAndCategory(
             @Param("sellerCif") String sellerCif,
             @Param("categoryName") String categoryName
     );
}
