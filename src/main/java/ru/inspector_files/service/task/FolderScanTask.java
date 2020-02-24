package ru.inspector_files.service.task;

import javafx.concurrent.Task;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import ru.inspector_files.utils.FolderVisitorImpl;

import java.io.File;
import java.nio.file.Files;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.inspector_files.utils.FileUtils.byteCountToDisplay;

public class FolderScanTask extends Task<Boolean> {
    private static final Logger logger = getLogger(FolderScanTask.class);
    private long allFolderSize;
    private long currentCounterSize;
    private final File folder;

    public FolderScanTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected Boolean call() {
        logger.info("Идет сканирование каталога: {}", folder.getAbsolutePath());
        try {
            allFolderSize = FileUtils.sizeOfDirectory(folder);
            FolderVisitorImpl visitor = new FolderVisitorImpl(this);
            Files.walkFileTree(folder.toPath(), visitor);
            return true;
        } catch (Exception e) {
            logger.error("Произошла ошибка при сканировании каталога: {}", folder.getAbsolutePath(), e);
            return false;
        }
    }

    public void updateFolderPath(String title) {
        super.updateTitle(title);
    }

    public void updateScanProgress(long workDone) {
        currentCounterSize += workDone;
        super.updateProgress(currentCounterSize, allFolderSize);
    }

    public void updateStateInMemory() {
        String message = byteCountToDisplay(currentCounterSize) + " of " + byteCountToDisplay(allFolderSize);
        super.updateMessage(message);
    }
}
