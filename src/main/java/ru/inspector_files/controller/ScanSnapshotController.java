package ru.inspector_files.controller;

import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.service.FolderVisitorService;
import ru.inspector_files.service.FolderVisitorServiceImpl;
import ru.inspector_files.ui.controls.FolderTreeItem;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScanSnapshotController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ScanSnapshotController.class);
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private FolderVisitorService service = new FolderVisitorServiceImpl(isRunning);
    @FXML
    private JFXTreeView<File> folderTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
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
        ObservableList<TreeItem<File>> children = folderTree.getRoot().getChildren();
        FolderTreeItem folderTreeItem = (FolderTreeItem) children.get(0);
        Set<File> folders = folderTreeItem.getSelectedFolders();
        isRunning.set(true);
        service.walk(folders);
    }

    @FXML
    public void onStop() {
        if (isRunning.get()) {
            logger.info("Процесс сканирования прерван вручную");
            isRunning.set(false);
        }
    }
}
