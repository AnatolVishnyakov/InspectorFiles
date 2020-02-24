package ru.inspector_files.controller.snapshot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class StopController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(StopController.class);
    private static final int CAPACITY_QUEUE = 4;
    @FXML
    public VBox indicatorScanFolder;
    @FXML
    private Pane scanProcessPane;
    private BlockingQueue<File> queue = new ArrayBlockingQueue<>(CAPACITY_QUEUE);
    private Map<String, Object> context;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            boolean isGetContext = false;
            while (!isGetContext) {
                context = (Map<String, Object>) scanProcessPane.getUserData();
                if (context != null) {
                    isGetContext = true;
                }
            }

            logger.info("Получили контекст");
            HashSet<File> folders = (HashSet<File>) context.get("folders");
            folders.forEach(folder -> {
                try {
                    logger.info("Добавление директории '{}' в очередь на сканирование", folder.getAbsolutePath());
                    queue.put(folder);
                    while (!queue.isEmpty()) {
                        File file = queue.take();
                        Executors.newSingleThreadExecutor().execute(() -> {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/snapshot/scan/FolderScanProgressComponent.fxml"));
                            loader.setControllerFactory(param -> {
                                Callable<?> controllerCallable = (Callable<ProcessController>) () -> new ProcessController(file);
                                try {
                                    return controllerCallable.call();
                                } catch (Exception ex) {
                                    throw new IllegalStateException(ex);
                                }
                            });

                            try {
                                Pane folderProcessPane = loader.load();
                                InterfaceExecutor.execute(() -> {
                                    folderProcessPane.setId(folder.getAbsolutePath());
                                    indicatorScanFolder.getChildren().add(folderProcessPane);
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    public void onStop() {
        AtomicBoolean isRunning = (AtomicBoolean) context.get("isRunning");
        isRunning.set(false);
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/scan/SnapshotRunPanel.fxml");
        BorderPane parent = (BorderPane) scanProcessPane.getParent();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(snapshotRunPanel);
        try {
            Pane content = loader.load();
            parent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}