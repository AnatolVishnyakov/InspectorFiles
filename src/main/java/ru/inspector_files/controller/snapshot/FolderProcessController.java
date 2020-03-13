package ru.inspector_files.controller.snapshot;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class FolderProcessController extends AbstractController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FolderProcessController.class);
    private static final String PANEL_FOLDER_STATUS_COMPONENT = "/view/snapshot/scan/FolderStatusComponent.fxml";
    private final List<Service<Boolean>> services = new ArrayList<>();
    @FXML
    public VBox indicator;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        HashSet<File> folders = (HashSet<File>) this.getUserData();
        folders.forEach(this::processHandler);
    }

    private void processHandler(File folder) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_FOLDER_STATUS_COMPONENT));
        loader.setControllerFactory(param -> initializeControllerCallable(folder));

        try {
            Pane folderProcessScreen = loader.load();
            InterfaceExecutor.execute(() -> {
                folderProcessScreen.setId(folder.getAbsolutePath());
                indicator.getChildren().add(folderProcessScreen);
            });
        } catch (IOException exc) {
            logger.error("Ошибка при загрузке панели {}", loader.getLocation());
            throw new RuntimeException(exc);
        }

        FolderProcessComponentController controller = loader.getController();
        Service<Boolean> service = controller.getService();
        services.add(service);
    }

    private Object initializeControllerCallable(File folder) {
        Callable<?> controller = (Callable<FolderProcessComponentController>) () -> new FolderProcessComponentController(folder);
        try {
            return controller.call();
        } catch (Exception exc) {
            logger.error("Ошибка при вызове контроллера {}", controller, exc);
            throw new RuntimeException(exc);
        }
    }

    @FXML
    public void onStop() {
        services.forEach(Service::cancel);
        setPanel("/view/snapshot/scan/FolderSnapshotScreen.fxml");
    }
}