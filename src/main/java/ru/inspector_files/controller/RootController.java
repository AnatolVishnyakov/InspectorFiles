package ru.inspector_files.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController extends AbstractController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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