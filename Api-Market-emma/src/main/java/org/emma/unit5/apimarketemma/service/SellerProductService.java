package org.emma.unit5.apimarketemma.service;

import org.emma.unit5.apimarketemma.model.dao.ISellerProductDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.SellerProductDTO;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerProductService {

    @Autowired
    ISellerProductDAO sellerProductDAO;

    public List<BigDecimal> getSellerNamesByOfferDates(LocalDate startDate, LocalDate endDate) {
        return sellerProductDAO.findOfferPriceByDates(startDate, endDate);
    }

    //Sin usar el DAO, llamamos a findAll para obtener todos los datos
    public List<SellerProduct> getAllSellerProducts() {
        return (List<SellerProduct>) sellerProductDAO.findAll();
    }

    /** @Autowired
    ISellersDAO sellersDAO;

    public List<SellerProductDTO> getAllSellerProducts() {
        List<SellerProduct> sellerProducts = (List<SellerProduct>) sellerProductDAO.findAll();
        return sellerProducts.stream()
                             .map(SellerProductDTO::new)
                             .collect(Collectors.toList());
    }

    public List<SellerProductDTO> getSellerProductsBySellerName(String sellerName) {
        Seller seller = sellersDAO.findByName(sellerName);
        if(seller == null){
            throw new IllegalArgumentException("Seller not found" + sellerName);
        }
        List<SellerProduct> sellerProducts = (List<SellerProduct>) sellerProductDAO.findBySeller(seller);
        return sellerProducts.stream()
                .map(SellerProductDTO::new)
                .collect(Collectors.toList());
    }**/
}
