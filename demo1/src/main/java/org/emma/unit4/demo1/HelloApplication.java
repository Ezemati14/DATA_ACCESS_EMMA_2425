package org.emma.unit4.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        if(doesDatabaseExist()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 605, 400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }

    }

    public static void main(String[] args) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("org.hibernate");
        logger.setLevel(Level.SEVERE);
        launch();
    }

    public static boolean doesDatabaseExist() {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/om-emma2024";
            connection = DriverManager.getConnection(dbUrl, "postgres", "boca");
            return true;
        } catch (Exception e) {
            System.err.println("Failed to connect tto database: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.err.println("Failed to recover connection: " + e.getMessage());
            }
        }
    }
}