package org.emma.unit5.apimarketemma.service;

import jakarta.transaction.Transactional;
import org.emma.unit5.apimarketemma.model.dao.ISellerProductDAO;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.SellerProductDTO;
import org.emma.unit5.apimarketemma.model.entities.Product;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.model.entities.SellerProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SellerProductService {

    @Autowired
    ISellerProductDAO sellerProductDAO;

    public void deleteProductById(Integer product) {
        sellerProductDAO.deleteById(product);  // Eliminar el producto por ID
    }

    public List<BigDecimal> getSellerNamesByOfferDates(LocalDate startDate, LocalDate endDate) {
        return sellerProductDAO.findOfferPriceByDates(startDate, endDate);
    }

    //Sin usar el DAO, llamamos a findAll para obtener todos los datos
    public List<SellerProduct> getAllSellerProducts() {
        return (List<SellerProduct>) sellerProductDAO.findAll();
    }

    //Llama al DAO que se le paso una query
    public List<SellerProduct> getSellerProductsByCif(String sellerCif) {
        List<SellerProduct> products = sellerProductDAO.findBySellerCifQuery(sellerCif);
        System.out.println("Seller Products: " + products);  // Verifica los productos obtenidos
        return products;
    }

    public Optional<SellerProduct> getSellerProductById(Integer id) {
        return sellerProductDAO.findById(id);
    }

    public List<SellerProduct> getOffersByCif(String cif) {
        return sellerProductDAO.findOffersByCif(cif);
    }
    public SellerProduct saveSellerProduct(SellerProduct sellerProduct) {
        return sellerProductDAO.save(sellerProduct);
    }

    public Optional<SellerProduct> updateSellerProduct(Integer id, SellerProduct updatedProduct) {
        return sellerProductDAO.findById(id).map(existingProduct -> {
            existingProduct.setProduct(updatedProduct.getProduct());
            existingProduct.setSeller(updatedProduct.getSeller());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setOfferPrice(updatedProduct.getOfferPrice());
            existingProduct.setOfferStartDate(updatedProduct.getOfferStartDate());
            existingProduct.setOfferEndDate(updatedProduct.getOfferEndDate());
            return sellerProductDAO.save(existingProduct);
        });
    }

    public Optional<SellerProduct> getByProductIdAndSellerId(Integer productId, Integer sellerId) {
        return sellerProductDAO.findByProductIdAndSellerId(productId, sellerId);
    }

}
