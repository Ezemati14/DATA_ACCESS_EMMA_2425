package org.emma.unit5.apimarketemma.model.dao;

import jakarta.transaction.Transactional;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISellerProductDAO extends CrudRepository<SellerProduct, Integer> {

    /** METODOS BASICOS DEL CRUD
     * save(Product p) → Guarda un producto.
     * findById(Integer id) → Busca por ID.
     * findAll() → Obtiene todos los productos.
     * delete(Product p) → Elimina un producto.
     * **/

    @Query(nativeQuery = true)
    List<SellerProduct> findBySellerCifQuery(
            @Param("sellerCif") String sellerCif
    );

    @Query("SELECT sp.offerPrice " +
            "FROM SellerProduct sp " +
            "WHERE sp.offerPrice IS NOT NULL " +
            "AND (sp.offerStartDate IS NULL OR sp.offerStartDate <= :endDate) " +
            "AND (sp.offerEndDate IS NULL OR sp.offerEndDate >= :startDate)")
    List<BigDecimal> findOfferPriceByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<SellerProduct> findBySellerCif(String sellerCif);

    @Query("SELECT sp FROM SellerProduct sp WHERE sp.product.id = :productId AND sp.seller.id = :sellerId")
    Optional<SellerProduct> findByProductIdAndSellerId(@Param("productId") Integer productId, @Param("sellerId") Integer sellerId);

    //Esta query nos devuelve las ofertas que tiene el cif que pasamos por parametro
    //ES un buscador para ver que ofertas tiene el cif
    //Este metodo es llamado del ViewController
    @Query("SELECT sp FROM SellerProduct sp WHERE sp.seller.cif = :cif AND sp.offerPrice IS NOT NULL")
    List<SellerProduct> findOffersByCif(@Param("cif") String cif);

}




/**
 * @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END FROM SellerProduct sp " +
"WHERE sp.product.id = :productId AND " +
"((:offerStartDate BETWEEN sp.offerStartDate AND sp.offerEndDate) OR " +
"(:offerEndDate BETWEEN sp.offerStartDate AND sp.offerEndDate))")
boolean existsByProductIdAndOfferPeriod(@Param("productId") Integer productId,
 @Param("offerStartDate") LocalDate offerStartDate,
 @Param("offerEndDate") LocalDate offerEndDate);
 **/
