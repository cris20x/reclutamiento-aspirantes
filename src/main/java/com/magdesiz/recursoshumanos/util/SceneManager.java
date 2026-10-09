package com.magdesiz.recursoshumanos.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SceneManager {

    private Stage stage;
    private Callback<Class<?>, Object> controllerFactory;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void start() {
        stage.setTitle("Recursos Humanos");
        stage.setScene(new Scene(new StackPane(), 600, 400));
        showLogin();
        stage.show();
    }

    public void showLogin() {
        show("login");
    }

    public void showRegister() {
        show("register");
    }

    public void showApplicants() {
        show("dashboard");
    }

    public void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void show(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + viewName + ".fxml"));
            loader.setControllerFactory(controllerFactory);
            Parent root = loader.load();
            stage.getScene().setRoot(root);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
