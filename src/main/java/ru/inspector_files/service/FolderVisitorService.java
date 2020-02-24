package ru.inspector_files.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import ru.inspector_files.service.task.FolderScanTask;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

public class FolderVisitorService extends Service<Boolean> {
    private static final Logger logger = getLogger(FolderVisitorService.class);
    private final File folder;

    public FolderVisitorService(File folder) {
        this.folder = folder;
    }

    @Override
    protected Task<Boolean> createTask() {
        logger.info("Создается задача на сканирование каталога: {}", folder.getAbsolutePath());
        return new FolderScanTask(folder);
    }
}
