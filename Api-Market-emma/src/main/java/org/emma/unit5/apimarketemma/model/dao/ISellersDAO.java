package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ISellersDAO extends CrudRepository<Seller, Integer> {

    Optional<Seller> findByCif(String cif);

    Seller findByName(String sellerName);

    Optional<Seller> findByCifAndPlainPassword(String cif, String password);
}
