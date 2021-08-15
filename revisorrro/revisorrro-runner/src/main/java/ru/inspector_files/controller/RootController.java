package ru.inspector_files.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private JFXButton buttonClose;
    @FXML
    private JFXButton buttonMinimize;
    @FXML
    private BorderPane contentPanel;
    private Map<Initializable, String> navigation = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonClose.addEventHandler(ActionEvent.ANY, mouseEvent -> Platform.exit());
        buttonMinimize.addEventHandler(ActionEvent.ANY, mouseEvent -> ((Stage) buttonMinimize.getScene().getWindow()).setIconified(true));
        onSnapshotPanel();
    }

    @FXML
    public void onDashboardButtonClick() {
        URL dashboardLayout = getClass().getResource("/view/DashboardPanel.fxml");
        setContentPanel(dashboardLayout);
    }

    @FXML
    public void onSnapshotPanel() {
        URL snapshotLayout = getClass().getResource("/view/SnapshotPanel.fxml");
        setContentPanel(snapshotLayout);
    }

    public void setContentPanel(URL view) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(view);
        try {
            Pane content = loader.load();
            contentPanel.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}