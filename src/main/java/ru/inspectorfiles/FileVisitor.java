package ru.inspectorfiles;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileVisitor {
    private final File root;
    private final File[] files;
    private final Map<String, Integer> cache = new HashMap<>();

    public FileVisitor(File root) {
        this.root = Objects.requireNonNull(root);
        this.files = Objects.requireNonNull(root.listFiles());
    }

    private void traversal(File file) {
        for (File currentFile : files) {
            String absolutePath = currentFile.getAbsolutePath();
            if (currentFile.isDirectory()) {
                cache.put(absolutePath, 0);
                visitChild(currentFile.getAbsolutePath(), currentFile);
            } else {
                cache.put(absolutePath, 1);
            }
        }
    }

    private void visitChild(String rootAbsolutePath, File file) {
        for (File currentFile: Objects.requireNonNull(file.listFiles())) {

        }
    }

    private void saveInfoFile() {

    }
}
