/*
 * Seller Products API
 * API para gestionar productos de vendedores
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import java.time.LocalDate;
import org.openapitools.client.model.SellerProduct;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SellerProductsApi
 */
@Disabled
public class SellerProductsApiTest {

    private final org.openapitools.client.api.SellerProductsApi api = new org.openapitools.client.api.SellerProductsApi();

    /**
     * Crear un nuevo producto
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createSellerProductTest() throws ApiException {
        SellerProduct sellerProduct = null;
        SellerProduct response = api.createSellerProduct(sellerProduct);
        // TODO: test validations
    }

    /**
     * Eliminar un producto por ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteSellerProductTest() throws ApiException {
        Integer id = null;
        api.deleteSellerProduct(id);
        // TODO: test validations
    }

    /**
     * Obtener todos los productos de vendedores
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAllSellerProductsTest() throws ApiException {
        List<SellerProduct> response = api.getAllSellerProducts();
        // TODO: test validations
    }

    /**
     * Obtener un producto por productId y sellerId
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getByProductAndSellerTest() throws ApiException {
        Integer productId = null;
        Integer sellerId = null;
        SellerProduct response = api.getByProductAndSeller(productId, sellerId);
        // TODO: test validations
    }

    /**
     * Obtener precios de productos por rango de fechas
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getOfferDateTest() throws ApiException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<Double> response = api.getOfferDate(startDate, endDate);
        // TODO: test validations
    }

    /**
     * Obtener ofertas activas de un vendedor por su CIF
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getOffersByCifTest() throws ApiException {
        String cif = null;
        List<SellerProduct> response = api.getOffersByCif(cif);
        // TODO: test validations
    }

    /**
     * Obtener un producto por ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSellerProductByIdTest() throws ApiException {
        Integer id = null;
        SellerProduct response = api.getSellerProductById(id);
        // TODO: test validations
    }

    /**
     * Obtener productos de un vendedor por su CIF
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSellerProductsByCifTest() throws ApiException {
        String cif = null;
        List<SellerProduct> response = api.getSellerProductsByCif(cif);
        // TODO: test validations
    }

    /**
     * Actualizar un producto por ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateSellerProductTest() throws ApiException {
        Integer id = null;
        SellerProduct sellerProduct = null;
        SellerProduct response = api.updateSellerProduct(id, sellerProduct);
        // TODO: test validations
    }

}
