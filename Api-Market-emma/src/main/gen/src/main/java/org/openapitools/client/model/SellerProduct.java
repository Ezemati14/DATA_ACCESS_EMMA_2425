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


package org.openapitools.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.lang.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * SellerProduct
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-03-04T10:37:48.180318300+01:00[Europe/Madrid]", comments = "Generator version: 7.7.0")
public class SellerProduct {
  public static final String SERIALIZED_NAME_PRODUCT_ID = "productId";
  @SerializedName(SERIALIZED_NAME_PRODUCT_ID)
  private Integer productId;

  public static final String SERIALIZED_NAME_SELLER_ID = "sellerId";
  @SerializedName(SERIALIZED_NAME_SELLER_ID)
  private Integer sellerId;

  public static final String SERIALIZED_NAME_PRODUCT_NAME = "productName";
  @SerializedName(SERIALIZED_NAME_PRODUCT_NAME)
  private String productName;

  public static final String SERIALIZED_NAME_PRICE = "price";
  @SerializedName(SERIALIZED_NAME_PRICE)
  private Double price;

  public static final String SERIALIZED_NAME_OFFER_START_DATE = "offerStartDate";
  @SerializedName(SERIALIZED_NAME_OFFER_START_DATE)
  private LocalDate offerStartDate;

  public static final String SERIALIZED_NAME_OFFER_END_DATE = "offerEndDate";
  @SerializedName(SERIALIZED_NAME_OFFER_END_DATE)
  private LocalDate offerEndDate;

  public SellerProduct() {
  }

  public SellerProduct productId(Integer productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Get productId
   * @return productId
   */
  @javax.annotation.Nullable
  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }


  public SellerProduct sellerId(Integer sellerId) {
    this.sellerId = sellerId;
    return this;
  }

  /**
   * Get sellerId
   * @return sellerId
   */
  @javax.annotation.Nullable
  public Integer getSellerId() {
    return sellerId;
  }

  public void setSellerId(Integer sellerId) {
    this.sellerId = sellerId;
  }


  public SellerProduct productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Get productName
   * @return productName
   */
  @javax.annotation.Nullable
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public SellerProduct price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  @javax.annotation.Nullable
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }


  public SellerProduct offerStartDate(LocalDate offerStartDate) {
    this.offerStartDate = offerStartDate;
    return this;
  }

  /**
   * Get offerStartDate
   * @return offerStartDate
   */
  @javax.annotation.Nullable
  public LocalDate getOfferStartDate() {
    return offerStartDate;
  }

  public void setOfferStartDate(LocalDate offerStartDate) {
    this.offerStartDate = offerStartDate;
  }


  public SellerProduct offerEndDate(LocalDate offerEndDate) {
    this.offerEndDate = offerEndDate;
    return this;
  }

  /**
   * Get offerEndDate
   * @return offerEndDate
   */
  @javax.annotation.Nullable
  public LocalDate getOfferEndDate() {
    return offerEndDate;
  }

  public void setOfferEndDate(LocalDate offerEndDate) {
    this.offerEndDate = offerEndDate;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SellerProduct sellerProduct = (SellerProduct) o;
    return Objects.equals(this.productId, sellerProduct.productId) &&
        Objects.equals(this.sellerId, sellerProduct.sellerId) &&
        Objects.equals(this.productName, sellerProduct.productName) &&
        Objects.equals(this.price, sellerProduct.price) &&
        Objects.equals(this.offerStartDate, sellerProduct.offerStartDate) &&
        Objects.equals(this.offerEndDate, sellerProduct.offerEndDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, sellerId, productName, price, offerStartDate, offerEndDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellerProduct {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    sellerId: ").append(toIndentedString(sellerId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    offerStartDate: ").append(toIndentedString(offerStartDate)).append("\n");
    sb.append("    offerEndDate: ").append(toIndentedString(offerEndDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("productId");
    openapiFields.add("sellerId");
    openapiFields.add("productName");
    openapiFields.add("price");
    openapiFields.add("offerStartDate");
    openapiFields.add("offerEndDate");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to SellerProduct
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!SellerProduct.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in SellerProduct is not found in the empty JSON string", SellerProduct.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!SellerProduct.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `SellerProduct` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("productName") != null && !jsonObj.get("productName").isJsonNull()) && !jsonObj.get("productName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `productName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("productName").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SellerProduct.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SellerProduct' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SellerProduct> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SellerProduct.class));

       return (TypeAdapter<T>) new TypeAdapter<SellerProduct>() {
           @Override
           public void write(JsonWriter out, SellerProduct value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SellerProduct read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of SellerProduct given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of SellerProduct
   * @throws IOException if the JSON string is invalid with respect to SellerProduct
   */
  public static SellerProduct fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SellerProduct.class);
  }

  /**
   * Convert an instance of SellerProduct to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

