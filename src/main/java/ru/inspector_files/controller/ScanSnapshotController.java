package ru.inspector_files.controller;

import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.ui.controls.FolderTreeItem;
import ru.inspector_files.utils.FolderVisitorImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScanSnapshotController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ScanSnapshotController.class);
    private AtomicBoolean isRunning = new AtomicBoolean(false);
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
        isRunning.set(true);
        ObservableList<TreeItem<File>> children = folderTree.getRoot().getChildren();
        FolderTreeItem folderTreeItem = (FolderTreeItem) children.get(0);
        Set<File> folders = folderTreeItem.getSelectedFolders();
        if (folders.isEmpty()) {
            logger.info("Не выбрана ни одна из директорий!");
            return;
        }
        logger.info("Запущен процесс сканирования директорий: {}", folders);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            logger.info(folders.toString());
            for (Iterator<File> iterator = folders.iterator(); iterator.hasNext() && isRunning.get(); ) {
                File folder = iterator.next();
                logger.info("Сканируется директория: {}", folder.toString());
                logger.info("Размер {} директории {}", folder, FileUtils.sizeOfDirectory(folder));
                try {
                    FolderVisitorImpl visitor = new FolderVisitorImpl();
                    visitor.setFlowCondition(isRunning);
                    Files.walkFileTree(folder.toPath(), visitor);
                } catch (IOException e) {
                    logger.error("Ошибка при обработке директории {}", folder, e);
                }
            }

            if (isRunning.get()) {
                // прошел полный обход директорий
                isRunning.set(false);
            }
            logger.info("Обход директорий завершен");
        });
    }

    @FXML
    public void onStop() {
        if (isRunning.get()) {
            logger.info("Процесс сканирования прерван вручную");
            isRunning.set(false);
        }
    }
}
