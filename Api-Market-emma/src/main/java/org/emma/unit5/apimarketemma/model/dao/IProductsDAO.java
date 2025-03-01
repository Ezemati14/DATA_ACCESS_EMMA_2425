package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductsDAO extends CrudRepository<Product, Integer> {

     /** CONSULTA PERSONALIZADA **/
     //Devuelve los nombres de los prodcutos
     @Query("SELECT p.productName FROM Product p")
     List<String> findAllProductNames();

     //Query para buscar productos de un seller, pasandole por paramtro el cif y categoria
     @Query(nativeQuery = true)
     List<Product> findMissingProductsBySellerAndCategory(
             //En estos param llega el cif y la categoria bebidas
             @Param("sellerCif") String sellerCif,
             @Param("categoryName") String categoryName
     );

     /** METODOS BASICOS DEL CRUD
      * save(Product p) → Guarda un producto.
      * findById(Integer id) → Busca por ID.
      * findAll() → Obtiene todos los productos.
      * delete(Product p) → Elimina un producto.
      * **/
}
