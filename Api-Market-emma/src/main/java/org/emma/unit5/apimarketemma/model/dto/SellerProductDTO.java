package org.emma.unit5.apimarketemma.model.dto;

import org.emma.unit5.apimarketemma.model.entities.SellerProduct;

import java.math.BigDecimal;

public class SellerProductDTO {
    private String sellerName;
    private String productName;
    private BigDecimal price;
    private BigDecimal offerPrice;
    private Integer stock;

    public SellerProductDTO(SellerProduct sellerProduct) {
        //Primero con getSeller obtenemos el objeto seller
        //Y luego ya dentro del objeto seller, recogemos el nombre getName()
        this.sellerName = sellerProduct.getSeller().getName();
        this.productName = sellerProduct.getProduct().getProductName();
        this.price = sellerProduct.getPrice();
        this.offerPrice = sellerProduct.getOfferPrice();
        this.stock = sellerProduct.getStock();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
