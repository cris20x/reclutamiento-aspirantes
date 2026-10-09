package com.magdesiz.recursoshumanos.controller;

import com.magdesiz.recursoshumanos.dto.RegisterDto;
import com.magdesiz.recursoshumanos.exception.AuthException;
import com.magdesiz.recursoshumanos.util.SceneManager;
import com.magdesiz.recursoshumanos.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    private AuthService authService;
    private SceneManager sceneManager;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public RegisterController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void register() {
        RegisterDto dto = new RegisterDto(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText());
        try {
            authService.register(dto);
            sceneManager.showAlert(AlertType.INFORMATION, "Registro exitoso. Ya puede iniciar sesión");
            sceneManager.showLogin();
        } catch (AuthException e) {
            sceneManager.showAlert(AlertType.WARNING, e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        sceneManager.showLogin();
    }
}
