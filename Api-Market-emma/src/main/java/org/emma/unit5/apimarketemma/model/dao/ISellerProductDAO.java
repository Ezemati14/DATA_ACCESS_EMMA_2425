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

public interface ISellerProductDAO extends CrudRepository<SellerProduct, Integer> {

    @Query("SELECT sp.offerPrice " +
            "FROM SellerProduct sp " +
            "WHERE sp.offerPrice IS NOT NULL " +
            "AND (sp.offerStartDate IS NULL OR sp.offerStartDate <= :endDate) " +
            "AND (sp.offerEndDate IS NULL OR sp.offerEndDate >= :startDate)")
    List<BigDecimal> findOfferPriceByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END FROM SellerProduct sp " +
            "WHERE sp.product.id = :productId AND " +
            "((:offerStartDate BETWEEN sp.offerStartDate AND sp.offerEndDate) OR " +
            "(:offerEndDate BETWEEN sp.offerStartDate AND sp.offerEndDate))")
    boolean existsByProductIdAndOfferPeriod(@Param("productId") Integer productId,
                                            @Param("offerStartDate") LocalDate offerStartDate,
                                            @Param("offerEndDate") LocalDate offerEndDate);

    List<SellerProduct> findBySellerCif(String sellerCif);

    //Obtenemos solo los productos del vendedro autenticado
   /** @Query(
            value = "SELECT p.* FROM products p " +
                    "JOIN seller_products sp ON p.product_id = sp.product_id " +
                    "JOIN sellers s ON sp.seller_id = s.seller_id " +
                    "WHERE s.cif = :sellerCif",
            nativeQuery = true
    )
    List<Product> findProductsBySellerCif(@Param("sellerCif") String sellerCif);**/
}
