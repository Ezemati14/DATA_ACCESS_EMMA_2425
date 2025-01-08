package org.emma.unit5.apimarketemma.service;

import jakarta.validation.Valid;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.SellerDTO;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class SellersService {

    @Autowired
    private ISellersDAO sellersDAO;

    public List<Seller> getAllSellers() {
        return (List<Seller>) sellersDAO.findAll();
    }

    public Seller getSellerByCif(String cif){
        return sellersDAO.findByCif(cif);
    }

    public void updateSeller(Integer id, SellerDTO sellerDTO) {
       Seller seller = sellersDAO.findById(id)
               .orElseThrow(() -> new RuntimeException("Seller not found"));;

       seller.setCif(sellerDTO.getCif());
       seller.setName(sellerDTO.getName());
       seller.setBusinessName(sellerDTO.getBusinessName());
       seller.setPhone(sellerDTO.getPhone());
       seller.setEmail(sellerDTO.getEmail());

       String encryptedPassword = encryptPassword(sellerDTO.getPlainPassword());
       seller.setPlainPassword(sellerDTO.getPlainPassword());
       seller.setPassword(encryptedPassword);

       sellersDAO.save(seller);
    }

    private String encryptPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }

    public Seller addSeller(@Valid SellerDTO sellerDTO) {
        Seller seller = new Seller();
        seller.setCif(sellerDTO.getCif());
        seller.setName(sellerDTO.getName());
        seller.setBusinessName(sellerDTO.getBusinessName());
        seller.setPhone(sellerDTO.getPhone());
        seller.setEmail(sellerDTO.getEmail());
        seller.setPlainPassword(sellerDTO.getPlainPassword());

        String encryptedPassword = encryptPassword(sellerDTO.getPlainPassword());
        seller.setPlainPassword(sellerDTO.getPlainPassword());
        seller.setPassword(encryptedPassword);

        seller.setPro(sellerDTO.getPro());
        seller.setUrl(sellerDTO.getUrl());

        return sellersDAO.save(seller);
    }

    public boolean deleteSeller(int id){
        if(sellersDAO.existsById(id)){
            sellersDAO.deleteById(id);
            return true;
        }
        return false;
    }
}
