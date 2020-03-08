package ru.inspector_files.controller.snapshot;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.controller.snapshot.mediator.SnapshotMediator;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class FolderProcessController implements Initializable, DataController {
    private static final Logger logger = LoggerFactory.getLogger(FolderProcessController.class);
    private static final int CAPACITY_QUEUE = 4;
    @FXML
    public VBox indicatorScanFolder;
    @FXML
    private Pane snapshotPane;
    private BlockingQueue<File> queue = new ArrayBlockingQueue<>(CAPACITY_QUEUE);
    private Map<String, Object> context;
    private List<Service<Boolean>> services = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        SnapshotMediator.getInstance().registerStopController(this);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            HashSet<File> folders = (HashSet<File>) SnapshotMediator.getInstance().getUserData(FolderSnapshotController.class);
            folders.forEach(folder -> {
                try {
                    logger.info("Добавление директории '{}' в очередь на сканирование", folder.getAbsolutePath());
                    queue.put(folder);
                    while (!queue.isEmpty()) {
                        File file = queue.take();
                        Executors.newSingleThreadExecutor().execute(() -> {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/snapshot/scan/FolderProcessScreen.fxml"));
                            loader.setControllerFactory(param -> {
                                Callable<?> controllerCallable = (Callable<FolderProcessComponentController>) () -> {
                                    FolderProcessComponentController folderProcessComponentController = new FolderProcessComponentController(file);
                                    return folderProcessComponentController;
                                };
                                try {
                                    return controllerCallable.call();
                                } catch (Exception ex) {
                                    throw new IllegalStateException(ex);
                                }
                            });

                            try {
                                Pane folderProcessPane = loader.load();
                                FolderProcessComponentController controller = loader.getController();
                                services.add(controller.getService());
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
        services.forEach(Service::cancel);
//        context.clear();
        URL snapshotRunPanel = getClass().getResource("/view/snapshot/scan/FolderSnapshotScreen.fxml");
        BorderPane parent = (BorderPane) snapshotPane.getParent();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(snapshotRunPanel);
        try {
            Pane content = loader.load();
            parent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getUserData() {
        return snapshotPane.getUserData();
    }
}