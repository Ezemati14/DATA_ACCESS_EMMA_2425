package org.emma.unit4.demo1.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.emma.unit4.demo1.dao.ProductsDAO;
import org.emma.unit4.demo1.dao.Seller_ProductsDAO;
import org.emma.unit4.demo1.model.Product;
import org.emma.unit4.demo1.model.Seller;
import org.emma.unit4.demo1.model.SellerProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OferAddController {

    private Seller seller;
    private ProductsDAO productsDAO = new ProductsDAO();
    private Seller_ProductsDAO seller_productsDAO = new Seller_ProductsDAO();
    private List<Product> list = new ArrayList<>();

    @FXML
    private ComboBox<Product> cb_product;
    @FXML private DatePicker dp_from;
    @FXML private DatePicker dp_to;
    @FXML private TextField tf_disc;
    @FXML private Button btn_add;
    @FXML private Label label_msg;
    @FXML private Label label_msg1;

    @FXML
    protected void initialize(){
        Platform.runLater(() -> cb_product.requestFocus());
    }

    @FXML
    public void setSeller(Seller seller) {
        this.seller = seller;
        list = productsDAO.getProductsFromSeller(seller.getId());

        setAllInputs();

        restrictDate(dp_from, LocalDate.now(), null);
        restrictDate(dp_to, LocalDate.now(), null);

        dp_to.setDisable(true);
    }

    @FXML
    public void callExit() {
        System.exit(0);
    }

    @FXML
    public void callAppView() {
        changeScene(seller);
    }

    @FXML
    public void firstDate(){
        LocalDate selectedDate = dp_from.getValue();
        if (selectedDate != null) {
            resetSecondDate(dp_to);
            restrictDate(dp_to, selectedDate, selectedDate.plusDays(29));
            dp_to.setDisable(false);
        }else{
            dp_to.setDisable(true);
        }
        updateDaysLabel();
    }

    @FXML
    public void secondDate(){
        LocalDate selectedDate = dp_to.getValue();
        if (selectedDate != null && dp_to.getValue() != null) {
            restrictDate(dp_from, LocalDate.now(), selectedDate);
            dp_from.setDisable(false);
        }
        updateDaysLabel();
    }

    private void restrictDate(DatePicker datePicker, LocalDate startDate, LocalDate endDate) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (startDate != null && endDate != null) {
                    if (date.isBefore(startDate) || date.isAfter(endDate)) {
                        setDisable(true);  // Deshabilitamos las fechas fuera del rango
                        setStyle("-fx-background-color: #ffcccc;");  // Color rojo para indicar las fechas deshabilitadas
                    }
                }
            }
        });
    }

    private void resetSecondDate(DatePicker dp_to) {
        dp_to.setValue(null);  // Reseteamos la fecha seleccionada en el segundo DatePicker
        dp_to.setDisable(true);  // Deshabilitamos el segundo DatePicker hasta que se seleccione el primer día
    }

    private void updateDaysLabel() {
        LocalDate fromDate = dp_from.getValue();
        LocalDate toDate = dp_to.getValue();

        if (fromDate != null && toDate != null) {
            long days = getDays(fromDate, toDate);
            label_msg1.setText("Days selected: " + days);
        } else {
            label_msg1.setText("Days selected: ...");
        }
    }

    @FXML
    public void checkOffer(){
        boolean allOk = true;
        String msg = "";

        showMsg("", "black");
        setStyleNull();

        if(cb_product.getValue()==null){
            cb_product.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            msg = "Please, select a product";
        }else if(dp_from.getValue() == null){
            dp_from.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            if(msg.trim().equals("")){
                msg = "Please, select the start of the offer";
            }
        } else if(dp_to.getValue() == null){
            dp_to.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            if(msg.trim().equals("")){
                msg = "Please, select the end of the offer";
            }
        } else {
            msg = checkDiscountValid();

            if (!msg.isEmpty()) {
                tf_disc.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                allOk = false;
            }
        }

        if(!allOk){
            showMsg(msg, "red");
            return;
        }

        // all good, calling the procedure
        boolean hasActiveOffer = true;

        if(seller.getPro()){
            hasActiveOffer = productsDAO.isPRODiscountActive(seller.getId(),dp_from.getValue(), dp_to.getValue());
        }else{
            hasActiveOffer = productsDAO.isDiscountActive(cb_product.getValue().getProdId(), dp_from.getValue(), dp_to.getValue());
        }

        if(!hasActiveOffer){
            //no active discounts for the product on those range of dates, call to create offer
            ButtonType action = createDialogConfirmation("Would you like to create the offer?");

            if (action == ButtonType.YES) {
                //modifying the offer with the new info
                Product product = cb_product.getValue();
                int discount = Integer.parseInt(tf_disc.getText());

                SellerProduct sellerProducts = seller_productsDAO.getFromSellerAndProduct(seller.getId(),product.getProdId());

                BigDecimal price = sellerProducts.getPrice();
                BigDecimal discountDecimal = new BigDecimal(discount / 100.0);
                BigDecimal discounted = price.multiply(discountDecimal);
                BigDecimal discountedPrice = price.subtract(discounted);

                discountedPrice = discountedPrice.setScale(2, RoundingMode.HALF_UP);

                sellerProducts.setOfferPrice(discountedPrice);
                sellerProducts.setOfferStartDate(dp_from.getValue());
                sellerProducts.setOfferEndDate(dp_to.getValue());

                SellerProduct res = seller_productsDAO.updateSeller(sellerProducts);

                if(res==null){
                    showMsg("There was an issue updating the offer. " +
                            "Please check the offer data and try again","red");
                }else{
                    showMsg("Offer correctly modified!","green");
                }

            } else if (action == ButtonType.NO || action == ButtonType.CANCEL) {
                //accion cancelada
            } else {
                System.out.println("Error: " + action.toString());
            }
        }else{
            //already active discount, tell user
            if(seller.getPro()){
                showMsg("This sellers has already three active offers\n" +
                        "for the selected date range. Please choose different dates.","red");
            }else {
                showMsg("This product already has an active offer\n" +
                        "for the selected date range. Please choose different dates.", "red");
            }
        }
    }

    @FXML
    public void createDialogInfo() {
        String msgDialog = "";

        if(seller.getPro()){
            msgDialog = "The discounted price for PRO sellers must match the following requirements:\n\n" +
                    "• For a 30-day period: maximum discount of 10%\n" +
                    "• For a 7-day period: maximum discount of 30%\n" +
                    "• For a 3-day period: maximum discount of 50%\n\n" +
                    "These type of PRO sellers can have three maximum offers at the same time.";
        }else{
            msgDialog = "The discounted price must match the following requirements:\n\n" +
                    "• For a 30-day period: maximum discount of 10%\n" +
                    "• For a 15-day period: maximum discount of 15%\n" +
                    "• For a 7-day period: maximum discount of 20%\n" +
                    "• For a 3-day period: maximum discount of 30%\n" +
                    "• For a 1-day period: maximum discount of 50%";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, msgDialog);
        alert.showAndWait();
    }

    public String checkDiscountValid(){
        String discount = tf_disc.getText();
        String message = "";

        if(!discount.equals("") && !discount.trim().isEmpty()){
            try{
                int i = Integer.parseInt(discount);

                //checking the discount
                if(dp_from != null && dp_to != null){
                    int maxDisc = offerMaximum(dp_from.getValue(), dp_to.getValue());

                    if (i > maxDisc) {
                        message = "Please, enter a valid discount for the range of dates \n("
                                + getDays(dp_from.getValue(), dp_to.getValue())
                                + " days), click the info button for more information";
                        return message;
                    }
                }
            }catch(NumberFormatException e){
                return "Please, enter a valid discount in a whole number format";
            }
        }else{
            return "Please, enter a discount to create the offer";
        }

        return message;
    }

    public ButtonType createDialogConfirmation(String msgDialog) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msgDialog, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult();
    }

    //function to clean up all red inputs
    public void setStyleNull() {
        cb_product.setStyle(null);
        dp_from.setStyle(null);
        dp_to.setStyle(null);
        tf_disc.setStyle(null);
    }

    public long getDays(LocalDate dateFrom, LocalDate dateTo){
        if (dateFrom != null && dateTo != null) {
            return ChronoUnit.DAYS.between(dateFrom, dateTo) + 1;
        }
        return 0;
    }

    private int offerMaximum(LocalDate dateFrom, LocalDate dateTo){
        int maxDiscount = 0;

        if(dateFrom !=null && dateTo != null){
            long daysBetween = getDays(dateFrom, dateTo);

            if (seller == null || seller.getPro() == null) {
                showMsg("INCOMPLETE INFORMATION", "red");
                return 0;
            }

            if(!seller.getPro()){
                if(daysBetween > 30){
                    showMsg("THE DISCOUNT CANNOT EXCEED 30 DAYS", "red");
                    return 0;
                }else if (daysBetween == 30) {
                    maxDiscount = 10;
                } else if (daysBetween < 30 && daysBetween >= 15) {
                    maxDiscount = 15;
                } else if (daysBetween < 15 && daysBetween >=7) {
                    maxDiscount = 20;
                } else if (daysBetween < 7 && daysBetween >= 3) {
                    maxDiscount = 30;
                } else if (daysBetween <= 2) {
                    maxDiscount = 50;
                } else {
                    showMsg("END DATE AFTER START DATE", "red");
                    return 0;
                }
            }else{
                if(daysBetween > 30){
                    showMsg("THE DISCOUNT CANNOT EXCEED 30 DAYS", "red");
                    return 0;
                }else if (daysBetween == 30) {
                    maxDiscount = 20;
                }else if (daysBetween < 30 && daysBetween >= 7) {
                    maxDiscount = 30;
                }else if (daysBetween < 7 && daysBetween >= 0) {
                    maxDiscount = 50;
                }
            }
        }else{
            showMsg("SELECT DATE TO APPLY DISCOUNT", "red");
        }

        return maxDiscount;
    }

    // function to format the date selected on the datepicker
    private void setDatePickerFormat(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) return "";
                return formatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, formatter);
            }
        });
    }

    private void disableInputs(boolean isEnable){
        cb_product.setDisable(isEnable);
        tf_disc.setDisable(isEnable);
        btn_add.setDisable(isEnable);

        dp_from.setDisable(isEnable);
        dp_from.getEditor().setDisable(true);
        dp_from.getEditor().setOpacity(1);

        dp_to.setDisable(isEnable);
        dp_to.getEditor().setDisable(true);
        dp_to.getEditor().setOpacity(1);
    }

    private void changeScene(Seller seller){
        try {
            System.out.printf("ESTAMOS EN OFERTA CONTROLLER ");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/emma/unit4/demo1/pers-data.fxml"));
            Pane appView = fxmlLoader.load();

            AppViewController appViewController = fxmlLoader.getController();
            appViewController.setSeller(seller);

            Stage stage = (Stage) cb_product.getScene().getWindow();

            Scene scene = new Scene(appView);
            stage.setScene(scene);
            stage.setTitle("Pers data");

            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showMsg(String msg, String color) {
        label_msg.setVisible(true);
        label_msg.setText(msg);
        label_msg.setStyle("-fx-text-fill: " + color + ";");
    }

    private void setAllInputs(){
        setDatePickerFormat(dp_from);
        setDatePickerFormat(dp_to);

        if(list.size()>0) {
            disableInputs(false);
            showMsg("","black");

            ObservableList<Product> productList = FXCollections.observableArrayList(list);
            cb_product.setItems(productList);

            forceNameComboBox();
        }else{
            showMsg("THE SELLER HAS NO PRODUCTS","black");
            disableInputs(true);
        }
    }

    public void forceNameComboBox() {
        cb_product.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product != null ? product.getProductName() : "";
            }

            @Override
            public Product fromString(String string) {
                return cb_product.getItems().stream()
                        .filter(product -> product.getProductName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

}
