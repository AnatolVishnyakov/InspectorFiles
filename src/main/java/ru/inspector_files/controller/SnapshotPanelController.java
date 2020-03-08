package ru.inspector_files.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SnapshotPanelController extends RootController implements Initializable {
    @FXML
    public JFXButton snapshotButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL snapshotCommonPanel = getClass().getResource("/view/snapshot/equals/SnapshotCommonPanel.fxml");
        setContentPanel(snapshotCommonPanel);
        onSnapshotButtonClick();
    }

    @FXML
    public void onSnapshotButtonClick() {
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/scan/FolderSnapshotScreen.fxml");
        setContentPanel(snapshotRunPanel);
    }

    @FXML
    public void onEqualsSnapshotButton() {
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/equals/SnapshotEqualsPanel.fxml");
        setContentPanel(snapshotRunPanel);
    }
}
