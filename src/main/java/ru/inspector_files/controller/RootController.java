package ru.inspector_files.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController extends AbstractController implements Initializable {
    @FXML
    private JFXButton buttonClose;
    @FXML
    private JFXButton buttonMinimize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonClose.addEventHandler(ActionEvent.ANY, mouseEvent -> Platform.exit());
        buttonMinimize.addEventHandler(ActionEvent.ANY, mouseEvent -> ((Stage) buttonMinimize.getScene().getWindow()).setIconified(true));
        onDashboardButtonClick(new ActionEvent());
    }

    @FXML
    public void onDashboardButtonClick(ActionEvent actionEvent) {
        URL dashboardLayout = getClass().getResource("/view/DashboardLayout.fxml");
        setContentPanel(dashboardLayout);
    }

    @FXML
    public void onRevisorButtonClick(ActionEvent actionEvent) {
        URL snapshotLayout = getClass().getResource("/view/SnapshotLayout.fxml");
        setContentPanel(snapshotLayout);
    }
}