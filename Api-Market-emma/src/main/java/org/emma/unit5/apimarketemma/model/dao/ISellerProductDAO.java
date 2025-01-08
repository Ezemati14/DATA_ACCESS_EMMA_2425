package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISellerProductDAO extends CrudRepository<SellerProduct, Integer> {
    List<SellerProduct> findBySeller(Seller seller);
}
