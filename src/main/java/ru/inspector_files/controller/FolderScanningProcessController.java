package ru.inspector_files.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.commons.io.FileUtils;
import ru.inspector_files.service.FolderVisitorService;
import ru.inspector_files.service.FolderVisitorServiceImpl;
import ru.inspector_files.to.ProgressComponent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class FolderScanningProcessController implements Initializable {
    @FXML
    public JFXProgressBar scanStatusProgressBar;
    @FXML
    public Label stateInMemoryLabel;
    @FXML
    public Label durationInPercentage;
    @FXML
    public Label folderPathLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File folder = new File("C:\\Program Files (x86)");
        ProgressComponent progressComponent = new ProgressComponent();
        progressComponent.setIsRunning(new AtomicBoolean(true));
        progressComponent.setFolderPathLabel(folderPathLabel);
        long sizeOfDirectory = FileUtils.sizeOfDirectory(folder);
        String size = " of " + sizeOfDirectory+ " bytes";
        stateInMemoryLabel.setUserData(sizeOfDirectory);
        stateInMemoryLabel.setText("0" + size);
        progressComponent.setStateInMemoryLabel(stateInMemoryLabel);
        progressComponent.setDurationInPercentage(durationInPercentage);
        progressComponent.setScanStatusProgressBar(scanStatusProgressBar);
        FolderVisitorService service = new FolderVisitorServiceImpl(progressComponent);
        service.walk(folder);
    }
}
