package ru.inspector_files.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.service.FolderVisitorService;
import ru.inspector_files.service.FolderVisitorServiceImpl;
import ru.inspector_files.ui.controls.FolderTreeItem;

import java.io.File;
import java.io.IOException;
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
    private Pane scanSnapshotPane;
    @FXML
    private JFXTreeView<File> folderTree;
    @FXML
    private JFXButton buttonScan;
    @FXML
    public JFXButton buttonStop;

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

        buttonScan.setDisable(false);
        buttonScan.addEventHandler(ActionEvent.ANY, mouseEvent -> {
            if (!getSelectedFolders().isEmpty()) {
                buttonScan.setDisable(true);
                buttonStop.setDisable(false);
            }
        });
        buttonStop.setDisable(true);
        buttonStop.addEventHandler(ActionEvent.ANY, mouseEvent -> {
            buttonScan.setDisable(false);
            buttonStop.setDisable(true);
        });
    }

    @FXML
    public void onScan() {
        isRunning.set(true);
        service.walk(getSelectedFolders());
        URL blockScreenLayout = getClass().getResource("/view/snapshot/ProgressScreenLayout.fxml");
        BorderPane parent = (BorderPane) scanSnapshotPane.getParent();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(blockScreenLayout);
        try {
            Pane content = loader.load();
            parent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onStop() {
        if (isRunning.get()) {
            logger.info("Процесс сканирования прерван вручную");
            isRunning.set(false);
        }
    }

    private Set<File> getSelectedFolders() {
        ObservableList<TreeItem<File>> children = folderTree.getRoot().getChildren();
        FolderTreeItem folderTreeItem = (FolderTreeItem) children.get(0);
        return folderTreeItem.getSelectedFolders();
    }
}
