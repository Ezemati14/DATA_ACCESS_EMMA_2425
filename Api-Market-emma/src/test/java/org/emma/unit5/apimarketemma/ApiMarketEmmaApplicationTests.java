package org.emma.unit5.apimarketemma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emma.unit5.apimarketemma.controller.SellersController;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = ApiMarketEmmaApplication.class)
@ExtendWith(MockitoExtension.class)
class ApiMarketEmmaApplicationTests {

    //Simula
    @Mock
    private SellersService sellersService;
    //Injectamos el clonn de ese
    @InjectMocks
    private SellersController sellersController;

    @Test
    void testGetAllSellers() throws Exception {
        Seller seller1 = new Seller(1, "A12345678", "John Doe", "Business One", "123456789", "john@example.com", "password", "hashedPassword", false, "http://example.com");
        Seller seller2 = new Seller(2, "B87654321", "Jane Doe", "Business Two", "987654321", "jane@example.com", "password", "hashedPassword", true, "http://example2.com");
        List<Seller> sellers = Arrays.asList(seller1, seller2);

        when(sellersService.getAllSellers()).thenReturn(sellers);

        List<Seller> allSellers = sellersController.getAllSellers();

        assertEquals(2,  allSellers.size());
    }

    @Test
    void testGetSellerByCifAndPassword_Found() {
        //Datos que quiero maneja
        String cif = "123456789";
        String password = "password";
        //Creo un seller para utilizar
        Seller seller = new Seller(1, cif, "John Doe", "Business One", "123456789", "john@example.com", password, "hashedPassword", false, "http://example.com");
        when(sellersService.findByCifAndPlainPassword(cif, password)).thenReturn(seller);
        // Llamar al controlador
        ResponseEntity<Seller> response = sellersController.getSellerByCifAndPassword(cif, password);
        // Validaciones
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateSeller() {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("Updated Name");

        doNothing().when(sellersService).updateSeller("A12345678", sellerDTO);

        ResponseEntity<String> response = sellersController.UpdateSeller("A12345678", sellerDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAddSeller() {
        //Creamos un objeto sellerDTO
        SellerDTO sellerDTO = new SellerDTO();
        //Le asignamos nombre
        sellerDTO.setName("New Seller");
        //Esto es un objeto seller que simulamos para verificar
        Seller newSeller = new Seller(1, "A12345678", "New Seller", "Business", "123456789", "new@example.com", "password", "hashedPassword", false, "http://example.com");
        //Con esto hacemos que cuando llamemos al addSeller, nos devuelve el newSeller
        //Y asi nos ahorramos interactuar con la base de datos
        when(sellersService.addSeller(sellerDTO)).thenReturn(newSeller);
        //Nos tiene que devolver lo mismo que el controlador, y el controlador intenta agregar al seller mockeado
        ResponseEntity<String> response = sellersController.addSeller(sellerDTO);
        //Esperamos el 201 HTTP created
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testAddSellerF() {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("Invalid Seller");

        when(sellersService.addSeller(sellerDTO)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = sellersController.addSeller(sellerDTO);

        assertEquals(400, response.getStatusCodeValue());
    }

}
