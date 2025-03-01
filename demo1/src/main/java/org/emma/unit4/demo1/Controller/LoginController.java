package org.emma.unit4.demo1.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.emma.unit4.demo1.dao.SellersDAO;
import org.emma.unit4.demo1.md5.TranslatePassword;
import org.emma.unit4.demo1.model.Seller;
import org.emma.unit4.demo1.unit.ObjectUnit;

import java.io.IOException;

public class LoginController {

    //Aca se almacena la informacion escrita en el hello-view.fxml
    @FXML private TextField field_user; //Dato que llega del textField cif
    @FXML private PasswordField field_pwd; //Dato que llega del textField password
    @FXML private Label label_msg; //Mensajes de confirmacion o error
    @FXML private CheckBox check_remember;
    @FXML private GridPane gridPane; //Tamaño y color del panel
    //Objeto para guarda informacion del usuario en un texto
    //y usarlo en un futuro
    ObjectUnit objReader = new ObjectUnit();

    @FXML
    protected void initialize(){

        gridPane.setStyle("-fx-background-color: #62c8e4;");

        if(readUserRemembered()){
            Platform.runLater(() -> field_pwd.requestFocus());
            check_remember.setSelected(true);
        }else {
            Platform.runLater(() -> field_user.requestFocus());
        }
    }

    //Este evento esta vinculado al archivo fxml
    @FXML
    protected void checkForUser() {
        //Con esto obtenemos los textos que el usuario escriba en el fxml
        String user = field_user.getText(); //Usuario
        String password = field_pwd.getText();//Contraseña

        //if para verificar si los campos estan vacios, con trim elimina los espacios en blanco
        //del inicio y final, length verifica si el campo es vacio
        if(user.trim().length() == 0 || password.trim().length() == 0){
            //Si uno de los 2 esta vacio, muestra este mensaje en rojo
            showMsg("Please, fill in all fields", "red");
        }else{
            //Esto convierte la constraseña ingresada, en una contraseña encriptada
            //Se llama al objeto y la funcion, y se le pasa por parametro esa contraseña
            String md5pass = TranslatePassword.trasPassword(password);
            //Se usar sellerDAO que es la encargada de interactuar con la base de datos
            SellersDAO sellersDAO = new SellersDAO();
            //Aca pasamos por paramtro el usario que viene del fxml y pass, que aca
            //estaria encriptado, por eso md5pass
            Seller userFinal = sellersDAO.getSellerFromUserAndPass(user, md5pass);

            //Si el usuario es null, se muestra ese mensaje
            if(userFinal==null){
                showMsg("INCORRECT NAME OR PASSWORD", "red");
            }else{
                //Este check_remember permite recordar el usuario
                //Si esta seleccionado cuado el userFinal en esa funcion
                if(check_remember.isSelected()){
                    writeRememberUser(userFinal);
                }else{
                    //Si no esta seleccionado lo elimina
                    objReader.cleanRememberUser();
                }

                showMsg("LOGIN CORRECT", "green");
                changeScene(userFinal);
            }
        }

    }

    //Esto guarda al usuario si se selecciono recordar usuario
    public void writeRememberUser(Seller seller){
        if(objReader.writeSellersToObj(seller)) {
            System.out.println("REMEMBER USER");
        }
    }
    //Recuerda al cif que esta en el archivo, y lo muestra por el textfield
    public boolean readUserRemembered(){
        Seller remembered = objReader.readObj();
        if(remembered!=null){
            field_user.setText(remembered.getCif());
            return true;
        }
        return false;
    }

    //Con esta funcion cambiamos de escena, se le pasa por parametro un Seller
    //es es el userFinal que viene de la funcion checkForUser que este usuario ya esta convalidado
    public void changeScene(Seller seller){
        try {
            //Se usar FXMLLoader para cargar el archivo pers-data.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/emma/unit4/demo1/pers-data.fxml"));
            Pane appView = fxmlLoader.load();
            //Obtenemos el controlador de la nueva vista AppViewController
            AppViewController appViewController = fxmlLoader.getController();
            //Y le pasamos la informacion del usuario autenticado
            //El seller que esta en este parametro, es el mismo que viene de checkForUser
            //el userFinal, que ya esta validado
            appViewController.setSeller(seller);
            Stage stage = (Stage) field_user.getScene().getWindow();

            Scene scene = new Scene(appView);
            stage.setScene(scene);
            stage.setTitle("Pers data");

            stage.show();
        } catch (IOException e) {
            System.err.println("Error cargando el archivo FXML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Funcion para mostrar mensajes
    public void showMsg(String msg, String color){
        label_msg.setText(msg);
        label_msg.setStyle("-fx-text-fill: " + color + ";");
    }

}
