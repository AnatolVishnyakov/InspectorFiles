package ru.inspector_files.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.DirectoryChooser;
import ru.inspector_files.ui.controls.FolderTreeItem;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ScanSnapshotController implements Initializable {
    @FXML
    private JFXComboBox comboBoxPath;
    @FXML
    private JFXTreeView<File> folderTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File[] localDisks = File.listRoots();

        TreeItem<File> root = new TreeItem<>();
        Arrays.stream(localDisks).forEach(disk -> {
            FolderTreeItem item = new FolderTreeItem(disk);
            root.getChildren().add(item);
        });
        folderTree.setRoot(root);
        folderTree.setShowRoot(false);
        folderTree.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    @FXML
    public void onScan(ActionEvent actionEvent) {
    }

    @FXML
    public void onStop(ActionEvent actionEvent) {
    }

    @FXML
    public void onSelectFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(folderTree.getScene().getWindow());

        if (selectedDirectory != null) {
            comboBoxPath.getItems().removeAll();
            comboBoxPath.getItems().add(selectedDirectory.getAbsoluteFile());
            comboBoxPath.getSelectionModel().select(0);
        }
    }
}
