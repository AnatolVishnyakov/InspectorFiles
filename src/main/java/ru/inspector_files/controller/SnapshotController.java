package ru.inspector_files.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SnapshotController extends AbstractController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL snapshotCommonPanel = getClass().getResource("/view/snapshot/SnapshotCommonPanel.fxml");
        setContentPanel(snapshotCommonPanel);
    }

    @FXML
    public void onSnapshotButtonClick(ActionEvent actionEvent) {
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/SnapshotRunPanel.fxml");
        setContentPanel(snapshotRunPanel);
    }

    @FXML
    public void onEqualsSnapshotButton(ActionEvent actionEvent) {
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/SnapshotEqualsPanel.fxml");
        setContentPanel(snapshotRunPanel);
    }
}
