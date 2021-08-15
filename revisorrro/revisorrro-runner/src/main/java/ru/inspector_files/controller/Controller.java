package ru.inspector_files.controller;

import javafx.fxml.Initializable;
import javafx.scene.Node;

public interface Controller extends Initializable {
    Node getView();

    void setView(Node view);

    void show();
}
