package ru.inspector_files.service;

import org.slf4j.Logger;
import ru.inspector_files.to.ProgressComponent;
import ru.inspector_files.utils.FolderVisitorImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.slf4j.LoggerFactory.getLogger;

public class FolderVisitorServiceImpl implements FolderVisitorService {
    private static final Logger logger = getLogger(FolderVisitorServiceImpl.class);
    private ProgressComponent progressComponent;

    public FolderVisitorServiceImpl(ProgressComponent progressComponent) {
        this.progressComponent = progressComponent;
    }

    @Override
    public void walk(File folder) {
        logger.info("Запущен процесс сканирования директорий: {}", folder);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            File[] folders = new File[]{folder};
            AtomicBoolean isRunning = progressComponent.getIsRunning();
            if (folders != null) {
                for (int i = 0; i < folders.length && isRunning.get(); i++) {
                    logger.info("Сканируется директория: {}", folders[i].toString());
                    try {
                        FolderVisitorImpl visitor = new FolderVisitorImpl(progressComponent);
                        Files.walkFileTree(folders[i].toPath(), visitor);
                    } catch (IOException e) {
                        logger.error("Ошибка при обработке директории {}", folders[i], e);
                    }
                }
            }

            if (isRunning.get()) {
                // прошел полный обход директорий
                isRunning.set(false);
            }
            logger.info("Обход директорий завершен");
        });
    }
}
