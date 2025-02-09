package org.emma.unit5.apimarketemma.model.dao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ISellersDAO extends CrudRepository<Seller, Integer> {

    Optional<Seller> findByCif(String cif);

    Seller findByName(String sellerName);

    Optional<Seller> findByCifAndPlainPassword(String cif, String password);

    List<Seller> cif(@Size(max = 20) @NotNull @Pattern(regexp = "^[A-Z][0-9]{8}$",  message = "CIF must start with a letter followed by 8 digits, with a maximum length of 9 characters") String cif);
}
