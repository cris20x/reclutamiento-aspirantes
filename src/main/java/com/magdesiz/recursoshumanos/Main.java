package com.magdesiz.recursoshumanos;
import com.magdesiz.recursoshumanos.controller.DashboardController;
import com.magdesiz.recursoshumanos.controller.LoginController;
import com.magdesiz.recursoshumanos.controller.RegisterController;
import com.magdesiz.recursoshumanos.repository.ApplicantRepository;
import com.magdesiz.recursoshumanos.repository.UsuarioRepository;
import com.magdesiz.recursoshumanos.util.SceneManager;
import com.magdesiz.recursoshumanos.service.DashboardService;
import com.magdesiz.recursoshumanos.service.AuthService;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);
        AuthService authService = new AuthService(new UsuarioRepository());
        DashboardService applicantService = new DashboardService(new ApplicantRepository());

        sceneManager.setControllerFactory(controllerClass -> {
            if (controllerClass == LoginController.class) {
                return new LoginController(authService, sceneManager);
            }
            if (controllerClass == RegisterController.class) {
                return new RegisterController(authService, sceneManager);
            }
            if (controllerClass == DashboardController.class) {
                return new DashboardController(applicantService, sceneManager);
            }
            throw new IllegalArgumentException(" controller: " + controllerClass.getName());
        });
        sceneManager.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
