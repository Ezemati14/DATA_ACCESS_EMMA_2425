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
    //Estos campos estan enlazados con el pers-data.fxml,
    //y llegan aca al controlador
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
    //Si se agerga un nuevo campo agregamos el FXML que viene del pers-data

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^https?:\\/\\/(www\\.)?[\\w-]+(\\.[\\w-]+)+(\\.[a-z]{2,6})(\\/[\\w-]*)*\\/?$");

    private Seller seller;

    //El seller que viene por parametro, viene del LoginController
    //en donde ya paso la validacion, y esta autenticado
    //Todos estos textfield se llenan automaticamente
    @FXML
    public void setSeller(Seller seller) {
        //Se obtiene el cif, y se muestra por el TextField del fxml
        //Aca lo que hacemos es obtener mediante get los datos de ese cif
        //y con el setText rellenamos esos TextFiedl
        tf_cif.setText(seller.getCif());
        tf_name.setText(seller.getName());
        tf_business.setText(seller.getBusinessName());
        tf_phone.setText(seller.getPhone());
        tf_email.setText(seller.getEmail());
        tf_password.setText(seller.getPlainPassword());
        tf_url.setText(seller.getUrl());
        //Al final se guarda los datos en esta variable
        //Esto sirve por si mas adelante queremos modificar datos y volver a estos
        //mismos datos
        this.seller = seller; //Se guardan los datos originales aca
    }
    //Esta funcion esta vinculada al boton de pers-data.fxml
    //Si se le da a reset, vuelve a los datos originales
    @FXML
    public void resetSeller() {
        putNull();
        //Esto hace que se vuelva a rellener los datos originales del seller
        //Cuando se le da al reset, vuelven a los valores de antes
        //Esto se logra porque el setSeller al pricipio guarda los datos originales
        tf_cif.setText(seller.getCif());
        tf_name.setText(seller.getName());
        tf_business.setText(seller.getBusinessName());
        tf_phone.setText(seller.getPhone());
        tf_email.setText(seller.getEmail());
        tf_password.setText(seller.getPlainPassword());
        tf_url.setText(seller.getUrl());
        //Cualquier mensaje que aparezca, lo borra para que se vuelva a crear
        showMsg("", "black");
    }

    //Boton enlazado con el pers-data que cuando se le da al boton,
    //actualiza los datos
    @FXML
    public void callUpdate() {
        //Con esto borramos cualquier mensaje que quede en la interfaz
        showMsg("", "black");
        putNull(); //y con este borramos los estilos

        //Aca obtenemos los valores que se ingresen en los TextField de pers-data
        String name = tf_name.getText();
        String business = tf_business.getText();
        String phone = tf_phone.getText();
        String email = tf_email.getText();
        String password = tf_password.getText();
        String url = tf_url.getText();
        //Con esto verificamos si hubo cambios en los datos
        boolean someChange = someChang(name, business, phone, email, password, url);

        if (someChange) {
            //Aca se valida la informacion ingresada
            String[][] fieldsToCheck = {
                    //Conjunto de reglas que deben seguir
                    //Nombre del campo[0], valor ingresado[1], maximo de caracteres[2]
                    //Contener solo numeros[3], Mail valido[4], puede estar vacio[5],
                    //si debe ser URL[6]
                    //{"Nombre del campo", valor, "longitud máxima", "checkNumber", "checkMail", "canBeNull", "checkUrl"}
                    {"Name", name, "100", "false", "false", "false", "false"},
                    {"Business Name", business, "100", "false", "false", "true", "false"},
                    {"Phone", phone, "15", "true", "false", "true", "false"},
                    {"Email", email, "90", "false", "true", "true", "false"},
                    {"URL", url, "200", "false", "false", "true", "true"},
                    {"Password", password, "50", "false", "false", "false", "false"},
            };

            int i = 0;
            //recorre el dieldsToCheck, con cada campo
            //El field es una fila de la matriz ejemplo:{"Name", name, "100", "false", "false", "false", "false"},
            for (String[] field : fieldsToCheck) {
                //Extrae los valores de la fila actual
                String fieldName = field[0];//Name
                String value = field[1];// name
                int maxChar = Integer.parseInt(field[2]);// 100
                boolean checkNumber = Boolean.parseBoolean(field[3]); //false
                boolean checkMail = Boolean.parseBoolean(field[4]);//false
                boolean canBeNull = Boolean.parseBoolean(field[5]);//false
                boolean checkUrl = Boolean.parseBoolean(field[6]);//false

                //Si esto retorna false significa que no cumple con las validaciones
                if (!checkValues(fieldName, value, maxChar, checkNumber, checkMail, !canBeNull, checkUrl)) {
                    switch (i) {
                        //Si la validacion falla, marca con un borde rojo y se detiene la ejecucion
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
            //Con esto mostramos una ventana emergente para aceptar si quiere modificar
            ButtonType action = createDialog("DO YOU WANT TO MODIFY SELLER?");
            //Si se marca yes, se crea un nuevo seller que en realidad es cambiar esos datos
            if (action == ButtonType.YES) {
                //Se le pasa al sellerDAO que actualizara los datos en la base de datos
                //Se crea un nuevo objeto seller con datos actualizados res
                Seller res = SellersDAO.sellerUpdate(new Seller(seller.getId(), seller.getCif(), name, business, phone, email, password, TranslatePassword.trasPassword(password), url, seller.getPro()));
                //Si no es null, entra a este if y envia un msj, y devuelve el nuevo seller
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
    //Este putNull lo que hace es eliminar los style de la pantalla
    //por ejemplo si aparece un texto en rojo, cuando se validen los datos bien
    //este texto tiene que desaparecer, y con putNull hacemos eso
    public void putNull() {
        tf_name.setStyle(null);
        tf_business.setStyle(null);
        tf_phone.setStyle(null);
        tf_email.setStyle(null);
        tf_password.setStyle(null);
        tf_url.setStyle(null);
    }

    //Funcion para comprobar que los datos no se tocaron
    public boolean someChang(String name, String business, String phone, String email, String password, String url) {
        //Comienza con que no hay ningun cambio
        boolean didChange = false;
        //Si el name es igual al otro name, el didChange cambia a true
        //Este name viene del textfield del callUpdate
        if (!seller.getName().equals(name)) {
            didChange = true;
        }

        //Si el objeto es null y si el nuevo valor no es null, osea que cambio
        if (seller.getBusinessName() == null && business != null) {
            //Ahora es true
            didChange = true;
            //Verifica si el valor original no es null, y si el nuevo valor es diferente al valor original
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
        //Si el valor check es null o esta vacio
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
