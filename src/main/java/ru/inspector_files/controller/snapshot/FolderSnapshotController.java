package ru.inspector_files.controller.snapshot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.ui.controls.FolderTree;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class FolderSnapshotController extends AbstractController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FolderSnapshotController.class);
    @FXML
    private JFXTreeView<File> folderTree;
    @FXML
    public JFXButton buttonScan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        buttonScan.setDisable(true);
        initializeFolderTreeView();
    }

    private void initializeFolderTreeView() {
        File[] localDisks = File.listRoots();

        TreeItem<File> root = new CheckBoxTreeItem<>();
        FolderTree.getSelectedFolders().clear();
        Arrays.stream(localDisks).forEach(disk -> {
            FolderTree item = new FolderTree(disk);
            item.selectedProperty().addListener((observable, oldValue, newValue) -> {
                buttonScan.disableProperty().bind(
                        Bindings.size(FXCollections.observableSet(FolderTree.getSelectedFolders())).isEqualTo(0)
                );
            });
            root.getChildren().add(item);
        });
        folderTree.setRoot(root);
        folderTree.setShowRoot(false);
        folderTree.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    @FXML
    public void onScan() {
        setUserData(getSelectedFolders());
        setPanel("/view/snapshot/scan/FolderProcessComponent.fxml");
    }

    private Set<File> getSelectedFolders() {
        ObservableList<TreeItem<File>> items = this.folderTree.getRoot().getChildren();
        return items.stream()
                .map(diskItem -> FolderTree.getSelectedFolders())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
