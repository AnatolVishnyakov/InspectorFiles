package ru.inspector_files.controller.snapshot;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.service.FolderVisitorService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class FolderProcessController extends AbstractController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FolderProcessController.class);
    private static final String PANEL_FOLDER_STATUS_COMPONENT = "/view/snapshot/scan/FolderStatusComponent.fxml";
    private static final String PANEL_FOLDER_SNAPSHOT_SCREEN = "/view/snapshot/scan/FolderSnapshotScreen.fxml";
    private final List<Service<Boolean>> services = new ArrayList<>();
    @FXML
    private VBox indicator;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        HashSet<File> folders = (HashSet<File>) this.getUserData();
        folders.forEach(folder -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_FOLDER_STATUS_COMPONENT));
            loader.setControllerFactory(param -> FolderProcessComponentController.controllerCallable(folder));

            Pane folderProcessScreen;
            try {
                folderProcessScreen = loader.load();
                folderProcessScreen.setId(folder.getAbsolutePath());
            } catch (IOException exc) {
                logger.error("Ошибка при загрузке панели {}", loader.getLocation());
                throw new RuntimeException(exc);
            }
            FolderVisitorService service = new FolderVisitorService(folder);
            service.setOnRunning(event -> indicator.getChildren().add(folderProcessScreen));
            service.setOnSucceeded(event -> {
                indicator.getChildren().remove(folderProcessScreen);
            });
            service.start();
            services.add(service);
        });
    }

    @FXML
    public void onStop() {
        services.forEach(Service::cancel);
        setPanel(PANEL_FOLDER_SNAPSHOT_SCREEN);
    }
}