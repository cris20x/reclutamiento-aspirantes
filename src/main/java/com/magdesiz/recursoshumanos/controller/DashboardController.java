package com.magdesiz.recursoshumanos.controller;

import com.magdesiz.recursoshumanos.dto.ApplicantDto;
import com.magdesiz.recursoshumanos.exception.ApplicantException;
import com.magdesiz.recursoshumanos.util.SceneManager;
import com.magdesiz.recursoshumanos.service.DashboardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {

    private DashboardService applicantService;
    private SceneManager sceneManager;
    private ObservableList<ApplicantDto> applicants = FXCollections.observableArrayList();

    @FXML
    private TableView<ApplicantDto> applicantsTable;

    @FXML
    private TableColumn<ApplicantDto, Integer> idColumn;

    @FXML
    private TableColumn<ApplicantDto, String> firstNameColumn;

    @FXML
    private TableColumn<ApplicantDto, String> lastNameColumn;

    @FXML
    private TableColumn<ApplicantDto, String> addressColumn;

    @FXML
    private TableColumn<ApplicantDto, Integer> ageColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField ageField;

    public DashboardController(DashboardService applicantService, SceneManager sceneManager) {
        this.applicantService = applicantService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        applicantsTable.setItems(applicants);
        applicantsTable.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
            if (selected != null) {
                fillForm(selected);
            }
        });

        try {
            applicants.setAll(applicantService.findAll());
        } catch (ApplicantException e) {
            sceneManager.showAlert(AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void add() {
        try {
            applicantService.add(readForm(0));
            refresh();
        } catch (ApplicantException e) {
            sceneManager.showAlert(AlertType.WARNING, e.getMessage());
        }
    }

    @FXML
    private void update() {
        try {
            applicantService.update(readForm(getSelected().getId()));
            refresh();
        } catch (ApplicantException e) {
            sceneManager.showAlert(AlertType.WARNING, e.getMessage());
        }
    }

    @FXML
    private void delete() {
        try {
            applicantService.delete(getSelected().getId());
            refresh();
        } catch (ApplicantException e) {
            sceneManager.showAlert(AlertType.WARNING, e.getMessage());
        }
    }

    @FXML
    private void logout() {
        sceneManager.showLogin();
    }

    private ApplicantDto getSelected() {
        ApplicantDto selected = applicantsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            throw new ApplicantException("Seleccione un aspirante de la tabla");
        }
        return selected;
    }

    private ApplicantDto readForm(int id) {
        int age;
        try {
            age = Integer.parseInt(ageField.getText().trim());
        } catch (NumberFormatException e) {
            throw new ApplicantException("La edad debe ser un número entero");
        }
        return new ApplicantDto(id,
                firstNameField.getText(),
                lastNameField.getText(),
                addressField.getText(),
                age);
    }

    private void fillForm(ApplicantDto dto) {
        firstNameField.setText(dto.getFirstName());
        lastNameField.setText(dto.getLastName());
        addressField.setText(dto.getAddress());
        ageField.setText(String.valueOf(dto.getAge()));
    }

    private void refresh() {
        applicants.setAll(applicantService.findAll());
        firstNameField.clear();
        lastNameField.clear();
        addressField.clear();
        ageField.clear();
    }
}
