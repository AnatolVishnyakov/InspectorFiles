package ru.inspector_files.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPanelController extends AbstractController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL dashboardPanel = getClass().getResource("/view/dashboard/DashboardPanel.fxml");
        setContentPanel(dashboardPanel);
    }
}
