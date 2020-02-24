package ru.inspector_files.controller.snapshot;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inspector_files.service.FolderVisitorService;
import ru.inspector_files.service.FolderVisitorServiceImpl;
import ru.inspector_files.to.ProgressComponent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.inspector_files.utils.FileUtils.byteCountToDisplay;

public class FolderScanningProcessController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FolderScanningProcessController.class);
    private final File scanFolder;
    @FXML
    public JFXProgressBar scanStatusProgressBar;
    @FXML
    public Label stateInMemoryLabel;
    @FXML
    public Label durationInPercentage;
    @FXML
    public Label folderPathLabel;

    public FolderScanningProcessController(File scanFolder) {
        this.scanFolder = scanFolder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Инициализация контроллера {}", getClass());
        ProgressComponent progressComponent = new ProgressComponent();
        progressComponent.setIsRunning(new AtomicBoolean(true));
        progressComponent.setFolderPathLabel(folderPathLabel);
        logger.info("Вычисляется размер содержимого директории '{}'", scanFolder.getAbsolutePath());
        long sizeOfDirectory = FileUtils.sizeOfDirectory(scanFolder);
        String size = " of " + byteCountToDisplay(sizeOfDirectory);
        logger.info("Вычислен размер директории '{}': {}", scanFolder.getAbsolutePath(), size);
        stateInMemoryLabel.setUserData(sizeOfDirectory);
        stateInMemoryLabel.setText("0" + size);
        progressComponent.setStateInMemoryLabel(stateInMemoryLabel);
        progressComponent.setDurationInPercentage(durationInPercentage);
        progressComponent.setScanStatusProgressBar(scanStatusProgressBar);
        scanStatusProgressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                Pane currentLayout = (Pane) scanStatusProgressBar.getParent();
                VBox parent = (VBox) currentLayout.getParent();
                parent.getChildren().remove(currentLayout);
            }
        });
        FolderVisitorService service = new FolderVisitorServiceImpl(progressComponent);
        service.walk(scanFolder);
    }
}
