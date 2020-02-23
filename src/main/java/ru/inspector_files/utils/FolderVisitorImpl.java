package ru.inspector_files.utils;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.control.Label;
import ru.inspector_files.service.DocumentService;
import ru.inspector_files.service.DocumentServiceImpl;
import ru.inspector_files.to.ProgressComponent;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.inspector_files.utils.FileUtils.byteCountToDisplay;

public class FolderVisitorImpl implements FileVisitor<Path> {
    private DocumentService service = new DocumentServiceImpl();
    private AtomicBoolean isRunning;
    private long sizeOfFolderCounter = 0;
    private int percentageProcess = 0;
    private final long sizeOfFolder;
    private final String sizeFolderLabel;
    private final JFXProgressBar scanStatusProgressBar;
    private final Label folderPathLabel;
    private final Label stateInMemoryLabel;
    private final Label durationInPercentage;

    public FolderVisitorImpl(ProgressComponent progressComponent) {
        this.isRunning = progressComponent.getIsRunning();
        this.folderPathLabel = progressComponent.getFolderPathLabel();
        this.stateInMemoryLabel = progressComponent.getStateInMemoryLabel();
        this.durationInPercentage = progressComponent.getDurationInPercentage();
        this.scanStatusProgressBar = progressComponent.getScanStatusProgressBar();
        this.sizeOfFolder = (long) stateInMemoryLabel.getUserData();
        this.sizeFolderLabel = " of " + byteCountToDisplay(sizeOfFolder);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (!isRunning.get()) {
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        File file = path.toFile();
        InterfaceExecutor.execute(() -> {
            folderPathLabel.setText(file.getAbsolutePath());
        });
        service.create(file);
        sizeOfFolderCounter += file.length();
        InterfaceExecutor.execute(() -> {
            stateInMemoryLabel.setText(byteCountToDisplay(sizeOfFolderCounter) + sizeFolderLabel);
            percentageProcess = (int) ((sizeOfFolderCounter * 100) / sizeOfFolder);
            durationInPercentage.setText(percentageProcess + "%");
            scanStatusProgressBar.setProgress((double) percentageProcess / 100);
        });
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
