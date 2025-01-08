package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.springframework.data.repository.CrudRepository;

public interface ISellersDAO extends CrudRepository<Seller, Integer> {

    Seller findByCif(String cif);

    Seller findByName(String sellerName);
}
