package ru.inspector_files.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class AbstractController {
    @FXML
    private BorderPane contentPanel;

    void setContentPanel(URL view) {
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
