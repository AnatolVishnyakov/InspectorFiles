package ru.inspector_files.service;

import org.slf4j.Logger;
import ru.inspector_files.utils.FolderVisitorImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.slf4j.LoggerFactory.getLogger;

public class FolderVisitorServiceImpl implements FolderVisitorService {
    private static final Logger logger = getLogger(FolderVisitorServiceImpl.class);
    private final AtomicBoolean isRunning;

    public FolderVisitorServiceImpl(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void walk(Set<File> folders) {
        if (folders.isEmpty()) {
            logger.info("Не выбрана ни одна из директорий!");
            return;
        }

        logger.info("Запущен процесс сканирования директорий: {}", folders);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            for (Iterator<File> iterator = folders.iterator(); iterator.hasNext() && isRunning.get(); ) {
                File folder = iterator.next();
                logger.info("Сканируется директория: {}", folder.toString());
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
}
