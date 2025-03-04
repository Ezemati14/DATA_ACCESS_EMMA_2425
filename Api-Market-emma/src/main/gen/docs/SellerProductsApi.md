# SellerProductsApi

All URIs are relative to *https://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSellerProduct**](SellerProductsApi.md#createSellerProduct) | **POST** /seller-products | Crear un nuevo producto |
| [**deleteSellerProduct**](SellerProductsApi.md#deleteSellerProduct) | **DELETE** /seller-products/{id} | Eliminar un producto por ID |
| [**getAllSellerProducts**](SellerProductsApi.md#getAllSellerProducts) | **GET** /seller-products | Obtener todos los productos de vendedores |
| [**getByProductAndSeller**](SellerProductsApi.md#getByProductAndSeller) | **GET** /seller-products/by-product-seller | Obtener un producto por productId y sellerId |
| [**getOfferDate**](SellerProductsApi.md#getOfferDate) | **GET** /seller-products/offer-date | Obtener precios de productos por rango de fechas |
| [**getOffersByCif**](SellerProductsApi.md#getOffersByCif) | **GET** /seller-products/offers/{cif} | Obtener ofertas activas de un vendedor por su CIF |
| [**getSellerProductById**](SellerProductsApi.md#getSellerProductById) | **GET** /seller-products/{id} | Obtener un producto por ID |
| [**getSellerProductsByCif**](SellerProductsApi.md#getSellerProductsByCif) | **GET** /seller-products/by-cif/{cif} | Obtener productos de un vendedor por su CIF |
| [**updateSellerProduct**](SellerProductsApi.md#updateSellerProduct) | **PUT** /seller-products/{id} | Actualizar un producto por ID |


<a id="createSellerProduct"></a>
# **createSellerProduct**
> SellerProduct createSellerProduct(sellerProduct)

Crear un nuevo producto

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    SellerProduct sellerProduct = new SellerProduct(); // SellerProduct | 
    try {
      SellerProduct result = apiInstance.createSellerProduct(sellerProduct);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#createSellerProduct");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sellerProduct** | [**SellerProduct**](SellerProduct.md)|  | |

### Return type

[**SellerProduct**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Producto creado correctamente |  -  |
| **400** | Datos inválidos |  -  |

<a id="deleteSellerProduct"></a>
# **deleteSellerProduct**
> deleteSellerProduct(id)

Eliminar un producto por ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    Integer id = 56; // Integer | ID del producto
    try {
      apiInstance.deleteSellerProduct(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#deleteSellerProduct");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| ID del producto | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Producto eliminado correctamente |  -  |
| **404** | Producto no encontrado |  -  |

<a id="getAllSellerProducts"></a>
# **getAllSellerProducts**
> List&lt;SellerProduct&gt; getAllSellerProducts()

Obtener todos los productos de vendedores

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    try {
      List<SellerProduct> result = apiInstance.getAllSellerProducts();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getAllSellerProducts");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;SellerProduct&gt;**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Lista de productos de vendedores obtenida correctamente |  -  |

<a id="getByProductAndSeller"></a>
# **getByProductAndSeller**
> SellerProduct getByProductAndSeller(productId, sellerId)

Obtener un producto por productId y sellerId

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    Integer productId = 56; // Integer | ID del producto
    Integer sellerId = 56; // Integer | ID del vendedor
    try {
      SellerProduct result = apiInstance.getByProductAndSeller(productId, sellerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getByProductAndSeller");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **productId** | **Integer**| ID del producto | |
| **sellerId** | **Integer**| ID del vendedor | |

### Return type

[**SellerProduct**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Producto encontrado |  -  |
| **404** | Producto no encontrado |  -  |

<a id="getOfferDate"></a>
# **getOfferDate**
> List&lt;Double&gt; getOfferDate(startDate, endDate)

Obtener precios de productos por rango de fechas

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    LocalDate startDate = LocalDate.now(); // LocalDate | Fecha de inicio del rango
    LocalDate endDate = LocalDate.now(); // LocalDate | Fecha de fin del rango
    try {
      List<Double> result = apiInstance.getOfferDate(startDate, endDate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getOfferDate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **startDate** | **LocalDate**| Fecha de inicio del rango | |
| **endDate** | **LocalDate**| Fecha de fin del rango | |

### Return type

**List&lt;Double&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Lista de precios obtenida correctamente |  -  |
| **400** | Parámetros de fecha inválidos |  -  |

<a id="getOffersByCif"></a>
# **getOffersByCif**
> List&lt;SellerProduct&gt; getOffersByCif(cif)

Obtener ofertas activas de un vendedor por su CIF

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    String cif = "cif_example"; // String | CIF del vendedor
    try {
      List<SellerProduct> result = apiInstance.getOffersByCif(cif);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getOffersByCif");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cif** | **String**| CIF del vendedor | |

### Return type

[**List&lt;SellerProduct&gt;**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Lista de ofertas obtenida correctamente |  -  |
| **404** | Vendedor no encontrado |  -  |

<a id="getSellerProductById"></a>
# **getSellerProductById**
> SellerProduct getSellerProductById(id)

Obtener un producto por ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    Integer id = 56; // Integer | ID del producto
    try {
      SellerProduct result = apiInstance.getSellerProductById(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getSellerProductById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| ID del producto | |

### Return type

[**SellerProduct**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Producto encontrado |  -  |
| **404** | Producto no encontrado |  -  |

<a id="getSellerProductsByCif"></a>
# **getSellerProductsByCif**
> List&lt;SellerProduct&gt; getSellerProductsByCif(cif)

Obtener productos de un vendedor por su CIF

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    String cif = "cif_example"; // String | CIF del vendedor
    try {
      List<SellerProduct> result = apiInstance.getSellerProductsByCif(cif);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#getSellerProductsByCif");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cif** | **String**| CIF del vendedor | |

### Return type

[**List&lt;SellerProduct&gt;**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Lista de productos obtenida correctamente |  -  |
| **404** | Vendedor no encontrado |  -  |

<a id="updateSellerProduct"></a>
# **updateSellerProduct**
> SellerProduct updateSellerProduct(id, sellerProduct)

Actualizar un producto por ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SellerProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://localhost:8080");

    SellerProductsApi apiInstance = new SellerProductsApi(defaultClient);
    Integer id = 56; // Integer | ID del producto
    SellerProduct sellerProduct = new SellerProduct(); // SellerProduct | 
    try {
      SellerProduct result = apiInstance.updateSellerProduct(id, sellerProduct);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SellerProductsApi#updateSellerProduct");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| ID del producto | |
| **sellerProduct** | [**SellerProduct**](SellerProduct.md)|  | |

### Return type

[**SellerProduct**](SellerProduct.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Producto actualizado correctamente |  -  |
| **404** | Producto no encontrado |  -  |
| **400** | Datos inválidos |  -  |

