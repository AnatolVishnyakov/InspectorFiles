package ru.inspector_files.controller;

import com.jfoenix.controls.JFXTreeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import ru.inspector_files.ui.controls.FolderTreeItem;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ScanSnapshotController implements Initializable {
    @FXML
    private JFXTreeView folderTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File rootFolder = new File("/");
        CheckBoxTreeItem<File> root = new FolderTreeItem(rootFolder);
        root.setExpanded(true);
        root.getChildren().addAll(getCheckBoxTreeItem(rootFolder));

        folderTree.setRoot(root);
        folderTree.setCellFactory(CheckBoxTreeCell.<File>forTreeView());
    }

    private List<CheckBoxTreeItem<File>> getCheckBoxTreeItem(File folder) {
        return getHierarchyFolders(folder).stream()
                .map(CheckBoxTreeItem::new)
                .collect(Collectors.toList());
    }

    private List<File> getHierarchyFolders(File folder) {
        return Arrays.stream(Objects.requireNonNull(folder.listFiles())).collect(Collectors.toList());
    }

    @FXML
    public void onScan(ActionEvent actionEvent) {
    }

    @FXML
    public void onStop(ActionEvent actionEvent) {
    }
}
