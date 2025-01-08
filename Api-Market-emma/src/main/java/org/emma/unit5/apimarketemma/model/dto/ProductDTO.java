package org.emma.unit5.apimarketemma.model.dto;

import org.emma.unit5.apimarketemma.model.entities.Product;

public class ProductDTO {
    private Integer id;
    private String productName;
    private String categoryName;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.categoryName = product.getCategory()  != null ? product.getCategory().getCategoryName() : null;
    }

    public Integer getId() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
