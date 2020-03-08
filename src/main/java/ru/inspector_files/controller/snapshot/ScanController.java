package ru.inspector_files.controller.snapshot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.ui.controls.FolderTreeItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScanController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ScanController.class);
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    @FXML
    private Pane scanSnapshotPane;
    @FXML
    private JFXTreeView<File> folderTree;
    @FXML
    public JFXButton buttonScan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        SnapshotMediator.getInstance().registerScanController(this);
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
    public void onScan() {
        isRunning.set(true);
        URL blockScreenLayout = getClass().getResource("/view/snapshot/scan/ProgressScreenLayout.fxml");
        BorderPane parent = (BorderPane) scanSnapshotPane.getParent();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(blockScreenLayout);
        try {
            Pane content = loader.load();
            content.setUserData(new HashMap<String, Object>() {
                {
                    put("isRunning", isRunning);
                    put("folders", getSelectedFolders());
                }
            });
            parent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<File> getSelectedFolders() {
        ObservableList<TreeItem<File>> children = folderTree.getRoot().getChildren();
        FolderTreeItem folderTreeItem = (FolderTreeItem) children.get(0);
        return folderTreeItem.getSelectedFolders();
    }
}
