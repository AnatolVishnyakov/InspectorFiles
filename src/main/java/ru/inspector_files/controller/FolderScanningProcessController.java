package ru.inspector_files.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FolderScanningProcessController implements Initializable {
    @FXML
    public JFXProgressBar scanStatusProgressBar;
    @FXML
    public Label stateInMemoryLabel;
    @FXML
    public Label durationInPercentage;
    @FXML
    public Label folderPathLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
