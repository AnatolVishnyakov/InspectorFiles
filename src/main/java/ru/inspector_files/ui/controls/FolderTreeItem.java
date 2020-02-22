package ru.inspector_files.ui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FolderTreeItem extends CheckBoxTreeItem<File> {
    private static final Logger logger = LoggerFactory.getLogger("logger.debug");
    private static final File[] EMPTY_ARRAY = {};
    private static final Set<File> selectedFolders = new HashSet<>();

    public FolderTreeItem(File file) {
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
            super.getChildren().setAll(buildChildren(this));
            TreeItem<File> parent = getParent();
            if (parent.getValue() == null) {
                selectedFolders.remove(getValue());
                System.out.println("remove: " + getValue());
            }
        });
        this.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedFolders.add(getValue());
            } else {
                selectedFolders.remove(getValue());
            }
        });
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        logger.debug("getChildren: {}", getValue());
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        logger.debug("isLeaf: {}", getValue());
        File[] files = getValue().listFiles(File::isDirectory);
        return files == null || files.length == 0;
    }

    private ObservableList<CheckBoxTreeItem<File>> buildChildren(CheckBoxTreeItem<File> item) {
        logger.debug("buildChildren: {}", item.getValue());
        File currentFile = item.getValue();
        if (hasFolders(currentFile)) {
            ObservableList<CheckBoxTreeItem<File>> children = FXCollections.observableArrayList();

            File[] folders = getFolders(currentFile);
            for (File childFolder : folders) {
                if (hasFolders(childFolder)) {
                    FolderTreeItem folderTreeItem = new FolderTreeItem(childFolder);
                    if (item.isSelected()) {
                        folderTreeItem.setSelected(true);
                    }
                    children.add(folderTreeItem);
                }
            }

            return children;
        }
        return FXCollections.emptyObservableList();
    }

    private boolean hasFolders(File folder) {
        if (folder != null) {
            File[] files = getFolders(folder);
            return files != null && files.length > 0;
        }
        return false;
    }

    private File[] getFolders(File folder) {
        if (folder != null) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                Object[] objects = Arrays.stream(files)
                        .filter(File::isDirectory)
                        .toArray();
                File[] results = new File[objects.length];
                System.arraycopy(objects, 0, results, 0, objects.length);
                return results;
            }
        }
        return EMPTY_ARRAY;
    }

    public Set<File> getSelectedFolders() {
        return selectedFolders;
    }
}