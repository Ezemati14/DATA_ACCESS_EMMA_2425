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

    @FXML private TextField field_user;
    @FXML private PasswordField field_pwd;
    @FXML private Label label_msg;
    @FXML private CheckBox check_remember;
    @FXML private GridPane gridPane;

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

    @FXML
    protected void checkForUser() {
        System.out.println("Método checkForUser ejecutado.");

        String user = field_user.getText();
        String password = field_pwd.getText();

        //check if the user or password has value
        if(user.trim().length() == 0 || password.trim().length() == 0){
            showMsg("Please, fill in all fields", "red");
        }else{
            String md5pass = TranslatePassword.trasPassword(password);

            SellersDAO sellersDAO = new SellersDAO();
            Seller userFinal = sellersDAO.getSellerFromUserAndPass(user, md5pass);

            if(userFinal==null){
                showMsg("INCORRECT NAME OR PASSWORD", "red");
            }else{
                if(check_remember.isSelected()){
                    writeRememberUser(userFinal);
                }else{
                    objReader.cleanRememberUser();
                }

                showMsg("LOGIN CORRECT", "green");
                changeScene(userFinal);
            }
        }

    }

    public void writeRememberUser(Seller seller){
        if(objReader.writeSellersToObj(seller)) {
            System.out.println("REMEMBER USER");
        }
    }

    public boolean readUserRemembered(){
        Seller remembered = objReader.readObj();
        if(remembered!=null){
            field_user.setText(remembered.getCif());
            return true;
        }
        return false;
    }

    public void changeScene(Seller seller){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/emma/unit4/demo1/pers-data.fxml"));
            Pane appView = fxmlLoader.load();

            AppViewController appViewController = fxmlLoader.getController();
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

    public void showMsg(String msg, String color){
        label_msg.setText(msg);
        label_msg.setStyle("-fx-text-fill: " + color + ";");
    }

}
