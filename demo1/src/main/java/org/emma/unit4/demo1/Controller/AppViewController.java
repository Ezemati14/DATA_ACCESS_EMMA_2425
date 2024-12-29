package org.emma.unit4.demo1.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.emma.unit4.demo1.dao.SellersDAO;
import org.emma.unit4.demo1.md5.TranslatePassword;
import org.emma.unit4.demo1.model.Seller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppViewController {
    @FXML
    private TextField tf_cif;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_business;
    @FXML
    private TextField tf_phone;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_url;
    @FXML
    private TextField tf_password;
    @FXML
    private Label label_msg;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^https?:\\/\\/(www\\.)?[\\w-]+(\\.[\\w-]+)+(\\.[a-z]{2,6})(\\/[\\w-]*)*\\/?$");

    private Seller seller;

    @FXML
    public void setSeller(Seller seller) {
        tf_cif.setText(seller.getCif());
        tf_name.setText(seller.getName());
        tf_business.setText(seller.getBusinessName());
        tf_phone.setText(seller.getPhone());
        tf_email.setText(seller.getEmail());
        tf_password.setText(seller.getPlainPassword());
        tf_url.setText(seller.getUrl());
        this.seller = seller;
    }

    @FXML
    public void resetSeller() {
        putNull();
        tf_cif.setText(seller.getCif());
        tf_name.setText(seller.getName());
        tf_business.setText(seller.getBusinessName());
        tf_phone.setText(seller.getPhone());
        tf_email.setText(seller.getEmail());
        tf_password.setText(seller.getPlainPassword());
        tf_url.setText(seller.getUrl());
        showMsg("", "black");
    }

    @FXML
    public void callUpdate() {
        showMsg("", "black");
        putNull();

        String name = tf_name.getText();
        String business = tf_business.getText();
        String phone = tf_phone.getText();
        String email = tf_email.getText();
        String password = tf_password.getText();
        String url = tf_url.getText();

        boolean someChange = someChang(name, business, phone, email, password, url);

        if (someChange) {
            String[][] fieldsToCheck = {
                    {"Name", name, "100", "false", "false", "false", "false"},
                    {"Business Name", business, "100", "false", "false", "true", "false"},
                    {"Phone", phone, "15", "true", "false", "true", "false"},
                    {"Email", email, "90", "false", "true", "true", "false"},
                    {"URL", url, "200", "false", "false", "true", "true"},
                    {"Password", password, "50", "false", "false", "false", "false"},
            };

            int i = 0;
            for (String[] field : fieldsToCheck) {
                String fieldName = field[0];
                String value = field[1];
                int maxChar = Integer.parseInt(field[2]);
                boolean checkNumber = Boolean.parseBoolean(field[3]);
                boolean checkMail = Boolean.parseBoolean(field[4]);
                boolean canBeNull = Boolean.parseBoolean(field[5]);
                boolean checkUrl = Boolean.parseBoolean(field[6]);

                if (!checkValues(fieldName, value, maxChar, checkNumber, checkMail, !canBeNull, checkUrl)) {
                    switch (i) {
                        case 0:
                            tf_name.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                        case 1:
                            tf_business.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                        case 2:
                            tf_phone.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                        case 3:
                            tf_email.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                        case 4:
                            tf_url.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                        case 5:
                            tf_password.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                            break;
                    }
                    return;
                }

                i++;
            }

            ButtonType action = createDialog("DO YOU WANT TO MODIFY SELLER?");

            if (action == ButtonType.YES) {
                Seller res = SellersDAO.sellerUpdate(new Seller(seller.getId(), seller.getCif(), name, business, phone, email, password, TranslatePassword.trasPassword(password), url, seller.getPro()));
                if(res!=null){
                    showMsg("UPDATE OK", "green");
                    setSeller(res);
                }else{
                    showMsg("SELLER CANNOT BE UPDATED", "red");
                }
            } else if (action == ButtonType.NO || action == ButtonType.CANCEL) {

            } else {
                System.out.println("Accion no controlada: " + action.toString());
            }
        }
    }

    public ButtonType createDialog(String msgDialog) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msgDialog, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult();
    }

    @FXML
    public void callExit() {
        System.exit(0);
    }

    @FXML
    public void OferView() {
        changeScene(seller);
    }

    public void changeScene(Seller seller){
        try {
            System.out.println("Ruta completa: " + getClass().getResource("/org/emma/unit4/demo1/ofer_add.fxml"));
            System.out.println("ESTAMOS EN EL APP VIEW CONTROLER");
            FXMLLoader fxmlLoader = new FXMLLoader(AppViewController.class.getResource("/org/emma/unit4/demo1/ofer-add.fxml"));

            Pane appView = fxmlLoader.load();

            OferAddController offerViewController = fxmlLoader.getController();
            offerViewController.setSeller(seller);

            Stage stage = (Stage) tf_cif.getScene().getWindow();

            Scene scene = new Scene(appView);
            stage.setScene(scene);
            stage.setTitle("Offer add");

            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cambiar la escena: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cambiar la escena", e);
        }
    }

    public void putNull() {
        tf_name.setStyle(null);
        tf_business.setStyle(null);
        tf_phone.setStyle(null);
        tf_email.setStyle(null);
        tf_password.setStyle(null);
        tf_url.setStyle(null);
    }

    public boolean someChang(String name, String business, String phone, String email, String password, String url) {
        boolean didChange = false;

        if (!seller.getName().equals(name)) {
            didChange = true;
        }

        if (seller.getBusinessName() == null && business != null) {
            didChange = true;
        } else if (seller.getBusinessName() != null && !seller.getBusinessName().equals(business)) {
            didChange = true;
        }

        if (seller.getPhone() == null && phone != null) {
            didChange = true;
        } else if (seller.getPhone() != null && !seller.getPhone().equals(phone)) {
            didChange = true;
        }

        if (seller.getEmail() == null && email != null) {
            didChange = true;
        } else if (seller.getEmail() != null && !seller.getEmail().equals(email)) {
            didChange = true;
        }

        if (!seller.getPlainPassword().equals(password)) {
            didChange = true;
        }

        if(!seller.getPassword().equals(password)){
            didChange = true;
        }

        if (!didChange) {
            showMsg("NO CHANGE DETECTED. ", "black");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkValues(String field, String check, int maxChar, boolean checkNumber, boolean checkMail, boolean checkEmpty, boolean checkUrl) {
        if (check == null || check.trim().isEmpty()) {
            if (checkEmpty) {
                showMsg("FILL IN FIELDS: " + field, "red");
                return false;
            } else {
                return true;
            }
        }

        if (check.length() > maxChar) {
            showMsg("FIELDS " + field + " LENGT MAXIMUS (" + maxChar + ").", "red");
            return false;
        }

        if (checkNumber) {
            Matcher matcher = PHONE_PATTERN.matcher(check);
            if (!matcher.matches()) {
                showMsg("FORMAT VALID (000-000-000).", "red");
                return false;
            }
        }

        if (checkMail) {
            Matcher matcher = EMAIL_PATTERN.matcher(check);
            if (!matcher.matches()) {
                showMsg("EMAILS NOT VALID", "red");
                return false;
            }
        }

        if (checkUrl) {
            Matcher matcher = URL_PATTERN.matcher(check);
            if (!matcher.matches()) {
                showMsg("URL NOT VALID", "red");
                return false;
            }
        }

        return true;
    }

    public void showMsg(String msg, String color) {
        label_msg.setVisible(true);
        label_msg.setText(msg);
        label_msg.setStyle("-fx-text-fill: " + color + ";");
    }
}
