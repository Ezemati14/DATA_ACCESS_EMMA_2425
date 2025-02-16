package org.emma.unit5.apimarketemma.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sellers_id_gen")
    @SequenceGenerator(name = "sellers_id_gen", sequenceName = "sellers_seller_id_seq", allocationSize = 1)
    @Column(name = "seller_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Pattern(regexp = "^[A-Z][0-9]{8}$",  message = "CIF must start with a letter followed by 8 digits, with a maximum length of 9 characters")
    @Column(name = "cif", nullable = false, length = 20)
    private String cif;

    @Size(max = 100)
    @NotNull
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    @Column(name = "business_name", length = 100)
    private String businessName;

    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only numbers")
    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Email(message = "Email should be valid")
    @Size(max = 90)
    @Column(name = "email", length = 90)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "plain_password", nullable = false, length = 50)
    private String plainPassword;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @ColumnDefault("false")
    @Column(name = "pro")
    private Boolean pro;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    public Seller(int i, String a12345678, String johnDoe, String businessOne, String number, String mail, String password, String hashedPassword, boolean b, String url) {
    }

    public Seller() {

    }

    public Integer getSellerId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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