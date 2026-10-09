package com.magdesiz.recursoshumanos.controller;

import com.magdesiz.recursoshumanos.dto.LoginDto;
import com.magdesiz.recursoshumanos.dto.UsuarioDto;
import com.magdesiz.recursoshumanos.exception.AuthException;
import com.magdesiz.recursoshumanos.util.SceneManager;
import com.magdesiz.recursoshumanos.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private AuthService authService;
    private SceneManager sceneManager;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void login() {
        LoginDto dto = new LoginDto(emailField.getText(), passwordField.getText());
        try {
            UsuarioDto user = authService.login(dto);
            passwordField.clear();
            sceneManager.showAlert(AlertType.INFORMATION,
                    "Bienvenido " + user.getFirstName() + ". Su rol es: " + user.getRole());
            sceneManager.showApplicants();
        } catch (AuthException e) {
            sceneManager.showAlert(AlertType.WARNING, e.getMessage());
        }
    }

    @FXML
    private void goToRegister() {
        sceneManager.showRegister();
    }
}
