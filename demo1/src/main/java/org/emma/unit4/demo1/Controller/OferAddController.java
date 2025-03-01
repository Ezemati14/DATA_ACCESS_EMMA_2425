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

    //Esta funcion almacena el seller osea sus datos, para luego usarlo mas adelante
    @FXML
    public void setSeller(Seller seller) {
        //Aca se guarda la infroamcion de ese seller
        this.seller = seller;
        //Este metodo va a tener todos los productos asociado a ese seller
        list = productsDAO.getProductsFromSeller(seller.getId());

        setAllInputs();
        //Restringue la selccion de fechas, impidiendo que se eliga fechas pasadas
        restrictDate(dp_from, LocalDate.now(), null);
        //No dejar que no se puede seleccionar una fecha anterior a la elegida
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

    //El proposito de esto es restringir las fechas
    @FXML
    public void firstDate(){
        //Seleccionamos el dato seleccionado por el usuario
        LocalDate selectedDate = dp_from.getValue();
        //Si selecciono una fecha valida
        if (selectedDate != null) {
            //Primero borra una fecha previamente, y limpia restrincciones
            resetSecondDate(dp_to);
            //Se restringue para que solamente se selecciona en un rango de 30 dias
            restrictDate(dp_to, selectedDate, selectedDate.plusDays(29));
            //Se habilita para el usuario pueda seleccionar
            dp_to.setDisable(false);
        }else{
            //Se desabilita si no hay primera fecha
            dp_to.setDisable(true);
        }
        //Esta funcion muestra cuantos dias se seleccionaron
        updateDaysLabel();
    }

    @FXML
    public void secondDate(){
        //Se selecciona la segunda fecha
        LocalDate selectedDate = dp_to.getValue();
        if (selectedDate != null && dp_to.getValue() != null) {
            //Se restringue para seleccionar fecha de hoy y la seleccionada
            restrictDate(dp_from, LocalDate.now(), selectedDate);
            dp_from.setDisable(false);//Se habilita por si esta desabhilitado
        }
        updateDaysLabel();
    }

    //Rebice por parametro DatePicker que aplica las restrcciones
    //startDate fecha miniuma permitida, endDate fecha maxima permitida
    private void restrictDate(DatePicker datePicker, LocalDate startDate, LocalDate endDate) {
        //Se usa para ver como se comportanlas celdas seleccionadas
        datePicker.setDayCellFactory(picker -> new DateCell() {
            //Se ejecuta para cada dia
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (startDate != null && endDate != null) {
                    //Si son validos
                    if (date.isBefore(startDate) || date.isAfter(endDate)) {
                        //Se desabhilitan las fechas antes y despues
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
    //Obtenemos los dias en total entre una fecha y otra para sacar los dias
    private void updateDaysLabel() {
        LocalDate fromDate = dp_from.getValue();// fehca de inicio
        LocalDate toDate = dp_to.getValue();//fecha de fin
        //Si son diferente de null
        if (fromDate != null && toDate != null) {
            long days = getDays(fromDate, toDate);//Nos devuelve los dias totales
            label_msg1.setText("Days selected: " + days);
        } else {
            //Mensaje que es predefinido
            label_msg1.setText("Days selected: ...");
        }
    }
    //Funcion del boton principal
    //Objetivo validar las fechas
    @FXML
    public void checkOffer(){
        boolean allOk = true; // se usa para verificar las validaciones
        String msg = "";

        showMsg("", "black");
        setStyleNull();//Limpia estilos previos en los campos

        //Si no hay ningun producto seleccionado muestra mensaje de error
        if(cb_product.getValue()==null){
            cb_product.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            msg = "Please, select a product";
        }
        //Si no hay fecha de inicio seleccionada, msj de error
        else if(dp_from.getValue() == null){
            dp_from.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            if(msg.trim().equals("")){
                msg = "Please, select the start of the offer";
            }
        }
        //Si no hay fecha de fin seleccionada, msj de error
        else if(dp_to.getValue() == null){
            dp_to.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            allOk = false;
            if(msg.trim().equals("")){
                msg = "Please, select the end of the offer";
            }
        }
        //Si no hay descuento ingresado, error
        else {
            msg = checkDiscountValid();

            if (!msg.isEmpty()) {
                tf_disc.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                allOk = false;
            }
        }
        //Si alguna validacion fallo se muestra msj en rojo y la funcion termina
        if(!allOk){
            showMsg(msg, "red");
            return;
        }
        //Comienza en true
        boolean hasActiveOffer = true;
        //compureba si existe fecha de descuento, si existe pasa de nuevo a true
        //si no devuelve false
        if(seller.getPro()){
            hasActiveOffer = productsDAO.isPRODiscountActive(seller.getId(),dp_from.getValue(), dp_to.getValue());
        }else{
            hasActiveOffer = productsDAO.isDiscountActive(cb_product.getValue().getProdId(), dp_from.getValue(), dp_to.getValue());
        }

        if(!hasActiveOffer){
            //Ventana que pregunta si quiere crear la oferta
            ButtonType action = createDialogConfirmation("Would you like to create the offer?");

            if (action == ButtonType.YES) {
                //modifying the offer with the new info
                Product product = cb_product.getValue();
                //el descuento que se puso en el textfield se pasa a integer, y se guarda en discount
                int discount = Integer.parseInt(tf_disc.getText());
                //Le pasamos por parametro a esta funcion el id, y el id de producto
                //y lo lleva a la query, y nos devuelve si se encuentra el producto o no
                SellerProduct sellerProducts = seller_productsDAO.getFromSellerAndProduct(seller.getId(),product.getProdId());
                //Como ya tenemos el PRODCUTO DE SELLER hacemos un getPrice() para sacer el precio a una variable price
                BigDecimal price = sellerProducts.getPrice();
                //Se divide por 100 el descuento que se ingreso en el textField = discount
                BigDecimal discountDecimal = new BigDecimal(discount / 100.0);
                //El resultado obtenido del descuento / 100 se guarda en discounted
                //y ese precio del producto se multiplica con ese descuento obtenido
                BigDecimal discounted = price.multiply(discountDecimal);

                BigDecimal discountedPrice = price.subtract(discounted);
                //esto hace que el descuento tenga solo 2 decimales
                discountedPrice = discountedPrice.setScale(2, RoundingMode.HALF_UP);
                //ahora se guardan los datos en la tabla sellerProduct
                //En esta se guarda en el campo de el precio con descuento
                sellerProducts.setOfferPrice(discountedPrice);
                //aca se gurada el valor del inicio de la fecha
                sellerProducts.setOfferStartDate(dp_from.getValue());
                //aca se gurada el final de la fecha
                sellerProducts.setOfferEndDate(dp_to.getValue());
                //Esto llama a esa funcion, y actualiza los datos de la tabla de datos
                SellerProduct res = seller_productsDAO.updateSeller(sellerProducts);

                if(res==null){
                    showMsg("There was an issue updating the offer. " +
                            "Please check the offer data and try again","red");
                }else{
                    showMsg("Offer correctly modified!","green");
                }

            } else if (action == ButtonType.NO || action == ButtonType.CANCEL) {

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
    //Ventana de informacion sobre los decuento permitidos
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

    //Verifica que el descuento sea valido
    public String checkDiscountValid(){
        String discount = tf_disc.getText();//Se obtiene el valor del textField de decuento
        String message = "";
        //Si el descuento no esta vacio, y no tiene esapcio
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
    //Funcion para el maximo de dias con descuento para vendedores PRO Y NO PRO
    private int offerMaximum(LocalDate dateFrom, LocalDate dateTo){
        int maxDiscount = 0;

        //Se comprueba que ambas fechas no sean null
        if(dateFrom !=null && dateTo != null){
            //calcula los dias entre las fechas
            long daysBetween = getDays(dateFrom, dateTo);
            //Si el seller es null, o si es pro es null, muestra msj en rojo
            //y retorna 0
            if (seller == null || seller.getPro() == null) {
                showMsg("INCOMPLETE INFORMATION", "red");
                return 0;
            }
            //Descuentos para vendedores NO PRO
            if(!seller.getPro()){
                //Si es mayor que 30 dias, msj en rojo de excedio los dias
                if(daysBetween > 30){
                    showMsg("THE DISCOUNT CANNOT EXCEED 30 DAYS", "red");
                    return 0;
                    //Si son 30 dias, maximo 10 dias
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
            }
            //Descuencto para los PRO
            else{
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

        return maxDiscount;//retornamos el descuento maximo
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

    // Por parametro se les pasa la fecha, y formatea las fechas
    private void setDatePickerFormat(DatePicker datePicker) {
        //Aca se crea el DateTimeFormatter esto quiere decir como la fecha se tiene que mostrar
        //Y lo guardo en una variable formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Esto convierte de string a LocalDate, y viseversa
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            //Este metodo convierte un objeto LocaDte en un String que se mostrara en el DataPicker
            public String toString(LocalDate localDate) {
                if (localDate == null) return ""; //Si localdate es null, retorna vacio
                //Si no es vacio, convirete el localdate en formato"dd/MM/yyyy" utilizando el formatter
                return formatter.format(localDate);
            }
            //Esto convierte un String en un objeto LocalDate
            @Override
            public LocalDate fromString(String dateString) {
                //Si es null o vacio, devuelve null
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, formatter);
            }
        });
    }

    private void setAllInputs(){
        setDatePickerFormat(dp_from);
        setDatePickerFormat(dp_to);
        //Verifica que la lista de productos tenga elementos
        if(list.size()>0) {
            //Si tiene prodcutos, se habilita los campos de entrar.
            disableInputs(false);
            showMsg("","black");
            //Esto convierte la lista de prodcutos en un ObservableList
            //Esto se hace por el combobox funciona con listas observables
            ObservableList<Product> productList = FXCollections.observableArrayList(list);
            //Una vez que lo pasamos a observableList, ese productList, lo agregamos al combobox
            //Y con esto podemos seleccionar los prodcutos
            //cb_product es el id del comboBox
            cb_product.setItems(productList);

            forceNameComboBox();
        }else{
            showMsg("THE SELLER HAS NO PRODUCTS","black");
            disableInputs(true); //Si no tiene queda desactivado
        }
    }
    //Habilita y desabhilita campos
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
    //Personaliza el comportamiento de un ComboBox
    //Solamente mostraremos el productName
    public void forceNameComboBox() {
        //Nos permite personlizar un objeto Prodcut en String
        //Esto lo podemos hacer cuando queremos solamente mostrar el nombre de un producto, y no toda la clase
        cb_product.setConverter(new StringConverter<Product>() {
            @Override
            //Este metodo convierte el objeto Product en String usando el getProductName
            public String toString(Product product) {
                //Si NO es null se devuelve asi
                //Si es NULL devuelve cadena vacia
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
