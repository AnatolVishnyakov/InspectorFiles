package ru.inspector_files.controller.snapshot;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.service.FolderVisitorService;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class FolderProcessComponentController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FolderProcessComponentController.class);
    private final File folder;
    @FXML
    public Label folderPath;
    @FXML
    public JFXProgressBar progress;
    @FXML
    public Label stateInMemoryLabel;
    @FXML
    public Label durationInPercentage;
    private Service<Boolean> service;

    public FolderProcessComponentController(File folder) {
        this.folder = folder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        progress.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                Pane currentLayout = (Pane) progress.getParent();
                VBox parent = (VBox) currentLayout.getParent();
                parent.getChildren().remove(currentLayout);
            }
        });

        service = new FolderVisitorService(folder);
        service.start();
        InterfaceExecutor.execute(() -> {
            folderPath.textProperty().bind(service.titleProperty());
            progress.progressProperty().bind(service.progressProperty());
            stateInMemoryLabel.textProperty().bind(service.messageProperty());
            durationInPercentage.textProperty().bind(
                    service.progressProperty()
                            .multiply(100)
                            .asString("%.0f %%")
            );
            durationInPercentage.visibleProperty().bind(service.progressProperty().isNotEqualTo(-1));
        });
    }

    public Service<Boolean> getService() {
        return service;
    }

    public static Object controllerCallable(File folder) {
        Callable<?> controller = (Callable<FolderProcessComponentController>) () -> new FolderProcessComponentController(folder);
        try {
            return controller.call();
        } catch (Exception exc) {
            logger.error("Ошибка при вызове контроллера {}", controller, exc);
            throw new RuntimeException(exc);
        }
    }
}
