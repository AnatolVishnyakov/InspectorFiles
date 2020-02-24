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

public class ProcessController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
    private final File folder;
    @FXML
    public Label folderPath;
    @FXML
    public JFXProgressBar progress;
    @FXML
    public Label stateInMemoryLabel;
    @FXML
    public Label durationInPercentage;

    public ProcessController(File folder) {
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

        Service<Boolean> service = new FolderVisitorService(folder);
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
}
