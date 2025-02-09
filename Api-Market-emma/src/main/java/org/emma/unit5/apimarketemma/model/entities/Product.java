package org.emma.unit5.apimarketemma.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.emma.unit5.apimarketemma.model.dto.ProductDTO;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "products")
@NamedNativeQuery(
        name = "Product.findMissingProductsBySellerAndCategory",
        query = "SELECT p.product_id, p.product_name, p.description, p.active, p.category_id " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.category_id " +
                "WHERE c.category_name = :categoryName " +
                "AND p.product_id NOT IN ( " +
                "    SELECT sp.product_id " +
                "    FROM seller_products sp " +
                "    JOIN sellers s ON sp.seller_id = s.seller_id " +
                "    WHERE s.cif = :sellerCif " +
                ")",
        resultClass = Product.class
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_gen")
    @SequenceGenerator(name = "products_id_gen", sequenceName = "products_product_id_seq", allocationSize = 1)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    public Integer getProductId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}