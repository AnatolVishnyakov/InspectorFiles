package ru.inspector_files.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import ru.inspector_files.service.task.FolderScanTask;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class FolderVisitorService extends Service<Boolean> {
    private static final Logger logger = getLogger(FolderVisitorService.class);
    private static final int THREAD_POOL_SIZE = 8;
    private static final long THREAD_TIME_OUT = 1000;
    private static final BlockingQueue<Runnable> IO_QUEUE = new LinkedBlockingQueue<Runnable>() {
        @Override
        public boolean offer(Runnable runnable) {
            if (FOLDERS_EXECUTOR.getPoolSize() < THREAD_POOL_SIZE) {
                return false;
            }
            return super.offer(runnable);
        }
    };
    private static final ThreadPoolExecutor FOLDERS_EXECUTOR = new ThreadPoolExecutor(
            2, THREAD_POOL_SIZE,
            THREAD_TIME_OUT, TimeUnit.MILLISECONDS,
            IO_QUEUE);
    {
        this.setExecutor(FOLDERS_EXECUTOR);
    }
    private final File folder;

    public FolderVisitorService(File folder) {
        this.folder = folder;
    }

    @Override
    protected Task<Boolean> createTask() {
        logger.info("Создается задача на сканирование каталога: {}", folder.getAbsolutePath());
        return new FolderScanTask(folder);
    }

    @Override
    public boolean cancel() {
        IO_QUEUE.clear();
        FOLDERS_EXECUTOR.purge();
        return super.cancel();
    }
}
