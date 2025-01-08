package org.emma.unit5.apimarketemma.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

public class SellerDTO {
    @NotNull
    @Pattern(regexp = "^[A-Z][0-9]{8}$", message = "CIF must start with a letter followed by 8 digits")
    private String cif;

    @NotNull
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Business name must contain only letters and spaces")
    private String businessName;

    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only numbers")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Size(max = 50, message = "Password must be less than 50 characters")
    private String plainPassword;

    @ColumnDefault("false")
    @Column(name = "pro")
    private Boolean pro;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Boolean getPro() {
        return pro;
    }

    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
