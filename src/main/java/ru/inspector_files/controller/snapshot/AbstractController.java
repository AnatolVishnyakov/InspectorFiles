package ru.inspector_files.controller.snapshot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractController {
    @FXML
    protected Pane snapshotPane;

    public void setPanel(String panel) {
        URL blockScreenLayout = getClass().getResource(panel);
        BorderPane parent = (BorderPane) snapshotPane.getParent();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(blockScreenLayout);
        try {
            Pane content = loader.load();
            parent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
