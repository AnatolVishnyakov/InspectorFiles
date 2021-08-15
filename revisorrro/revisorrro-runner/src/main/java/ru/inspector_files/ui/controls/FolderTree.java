package ru.inspector_files.ui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

public class FolderTree extends CheckBoxTreeItem<File> {
    private static final Logger logger = LoggerFactory.getLogger("logger.debug");
    private static final File[] EMPTY_ARRAY = {};
    private static final ObservableSet<File> selectedFolders = FXCollections.observableSet();

    public FolderTree(File file) {
        super(new File(file.getAbsolutePath()) {
            @Override
            public String toString() {
                if (this.getName().isEmpty()) {
                    return this.getAbsolutePath();
                }
                return this.getName();
            }
        });
        this.expandedProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug("expanded");
            super.getChildren().setAll(buildChildren(this));
            TreeItem<File> parent = getParent();
            if (parent.getValue() == null) {
                logger.debug("remove parent: {}", getValue());
                selectedFolders.remove(getValue());
            }
        });
        this.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                logger.debug("selected value: {}", getValue());
                selectedFolders.add(getValue());
            } else {
                logger.debug("unselected value: {}", getValue());
                selectedFolders.remove(getValue());
            }
        });
    }

    @Override
    public boolean isLeaf() {
        logger.debug("isLeaf: {}", getValue());
        return !getValue().isDirectory();
    }

    private ObservableList<CheckBoxTreeItem<File>> buildChildren(CheckBoxTreeItem<File> item) {
        logger.debug("buildChildren: {}", item.getValue());
        File currentFile = item.getValue();
        if (currentFile.isDirectory()) {
            ObservableList<CheckBoxTreeItem<File>> children = FXCollections.observableArrayList();

            File[] folders = getFolders(currentFile);
            for (File childFolder : folders) {
                if (childFolder.isDirectory()) {
                    FolderTree folderTree = new FolderTree(childFolder);
                    if (item.isSelected()) {
                        folderTree.setSelected(true);
                    }
                    children.add(folderTree);
                }
            }

            return children;
        }
        return FXCollections.emptyObservableList();
    }

    private File[] getFolders(File folder) {
        logger.debug("getFolders: {}", folder);
        if (folder != null) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                Object[] objects = Arrays.stream(files)
                        .filter(File::isDirectory)
                        .toArray();
                Object[] results = new File[objects.length];
                System.arraycopy(objects, 0, results, 0, objects.length);
                return (File[]) results;
            }
        }
        return EMPTY_ARRAY;
    }

    public static ObservableSet<File> getSelectedFolders() {
        return selectedFolders;
    }
}
