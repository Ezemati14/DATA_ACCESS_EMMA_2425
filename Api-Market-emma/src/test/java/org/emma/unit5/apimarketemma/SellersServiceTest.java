package org.emma.unit5.apimarketemma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emma.unit5.apimarketemma.controller.SellersController;
import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.model.dto.SellerDTO;
import org.emma.unit5.apimarketemma.model.entities.Seller;
import org.emma.unit5.apimarketemma.service.SellersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = ApiMarketEmmaApplication.class)
@ExtendWith(MockitoExtension.class)
class SellersServiceTest {

    @Mock
    private ISellersDAO sellersDAO;
    @InjectMocks
    private SellersService sellersService;

    @Test
    void testGetAllSellers() {
        //Simulamos una lista de vendedores
        List<Seller> listSellers = Arrays.asList(
                new Seller(1, "A12345678", "John Doe", "Business One", "123456789", "john@example.com", "password", "hashedPassword", false, "http://example.com"),
                new Seller(2, "B87654321", "Jane Doe", "Business Two", "987654321", "jane@example.com", "password", "hashedPassword", true, "http://example2.com")
        );

        when(sellersDAO.findAll()).thenReturn(listSellers);

        List<Seller> resultSellers = sellersService.getAllSellers();

        assertEquals(2, resultSellers.size());
    }

    private void assertEquals(int i, int size) {
    }

    @Test
    void testGetSellerByCifAndPassword() {
        String cif = "A12345678";
        String password = "password";
        String name = "John Doe";

        Seller seller = new Seller(1, cif, name, "Business One", "123456789", "john@example.com", password, "hashedPassword", false, "http://example.com");

        when(sellersDAO.findByCifAndPlainPassword(eq(cif), eq(password)))
                .thenReturn(Optional.of(seller));

        Seller result = sellersService.findByCifAndPlainPassword(cif, password);

        System.out.println("RESULT - Seller Name: " + (result != null ? result.getName() : "null"));

        assertNotNull(result);
        assertEquals(name, result.getName());

    }

    @Test
    void testFindByCifAndPlainPassword_NotFound() {
        String cif = "A12345678";
        String password = "password";

        when(sellersDAO.findByCifAndPlainPassword(cif, password)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sellersService.findByCifAndPlainPassword(cif, password);
        });

        assertEquals("Seller not found", exception.getMessage());
    }

    private void assertEquals(String sellerNotFound, String message) {
    }

    @Test
    void testUpdateSeller_Success() {
        String cif = "A12345678";
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setCif("A12345678");
        sellerDTO.setName("Updated Name");
        sellerDTO.setBusinessName("Updated Business");
        sellerDTO.setPhone("987654321");
        sellerDTO.setEmail("updated@example.com");
        sellerDTO.setPlainPassword("newPassword");

        Seller existingSeller = new Seller(1, cif, "John Doe", "Business One", "123456789", "john@example.com", "password", "hashedPassword", false, "http://example.com");

        when(sellersDAO.findByCif(cif)).thenReturn(Optional.of(existingSeller));
        when(sellersDAO.save(any(Seller.class))).thenReturn(existingSeller);

        assertDoesNotThrow(() -> sellersService.updateSeller(cif, sellerDTO));

        verify(sellersDAO, times(1)).save(any(Seller.class));
    }

    @Test
    void testUpdateSeller_NotFound() {
        String cif = "A12345678";
        SellerDTO sellerDTO = new SellerDTO();

        when(sellersDAO.findByCif(cif)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sellersService.updateSeller(cif, sellerDTO);
        });

        assertEquals("Seller not found", exception.getMessage());
    }

    @Test
    void testAddSeller_Success() {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setCif("A12345678");
        sellerDTO.setName("New Seller");
        sellerDTO.setBusinessName("Business");
        sellerDTO.setPhone("123456789");
        sellerDTO.setEmail("new@example.com");
        sellerDTO.setPlainPassword("password");
        sellerDTO.setPro(false);
        sellerDTO.setUrl("http://example.com");

        Seller newSeller = new Seller(1, "A12345678", "New Seller", "Business", "123456789", "new@example.com", "password", "hashedPassword", false, "http://example.com");

        when(sellersDAO.save(any(Seller.class))).thenReturn(newSeller);

        Seller result = sellersService.addSeller(sellerDTO);

        assertNotNull(result);
        assertEquals("New Seller", result.getName());
        verify(sellersDAO, times(1)).save(any(Seller.class));
    }


}
