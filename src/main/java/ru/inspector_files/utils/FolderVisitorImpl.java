package ru.inspector_files.utils;

import ru.inspector_files.service.DocumentService;
import ru.inspector_files.service.DocumentServiceImpl;
import ru.inspector_files.service.task.FolderScanTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderVisitorImpl implements FileVisitor<Path> {
    private DocumentService service = new DocumentServiceImpl();
    private final FolderScanTask task;

    public FolderVisitorImpl(FolderScanTask task) {
        this.task = task;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        task.updateFolderPath(dir.toFile().getAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        File file = path.toFile();
        service.create(file);
        task.updateScanProgress(file.length());
        task.updateStateInMemory();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        exc.printStackTrace();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
