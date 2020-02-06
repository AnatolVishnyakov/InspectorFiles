package ru.inspector_files.ui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Arrays;

public class FolderTreeItem extends CheckBoxTreeItem<File> {
    private static final File[] EMPTY_ARRAY = {};
    private boolean isFirstTimeChildren = true;

    public FolderTreeItem(File file) {
        super(new File(file.getAbsolutePath()) {
            @Override
            public String toString() {
                return getName();
            }
        });
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        File file = getValue();
        return !hasFolders(file);
    }

    private ObservableList<CheckBoxTreeItem<File>> buildChildren(CheckBoxTreeItem<File> item) {
        File currentFile = item.getValue();
        if (hasFolders(currentFile)) {
            ObservableList<CheckBoxTreeItem<File>> children = FXCollections.observableArrayList();

            File[] folders = getFolders(currentFile);
            for (File childFolder : folders) {
                if (hasFolders(childFolder)) {
                    children.add(new FolderTreeItem(childFolder));
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
}
