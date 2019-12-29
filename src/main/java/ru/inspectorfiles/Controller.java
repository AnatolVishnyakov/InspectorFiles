package ru.inspectorfiles;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private SimpleStringProperty labelStatus = new SimpleStringProperty(this, "value");
    private AtomicInteger counter = new AtomicInteger(0);
    @FXML
    private Button buttonScan;
    @FXML
    public Button buttonStop;
    @FXML
    private ChoiceBox<File> choiceBoxDisks;
    @FXML
    private Label labelCountFiles;
    private volatile boolean isStop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<File> disks = FXCollections.observableArrayList(File.listRoots());
        disks.add(new File("C:/Windows")); // TODO
        choiceBoxDisks.setItems(disks);
        choiceBoxDisks.setValue(disks.get(0));
        labelCountFiles.textProperty().bind(Bindings.convert(labelStatus));
        buttonStop.setDisable(true);
        isStop = false;
    }

    @FXML
    public void onClickScan() {
        isStop = false;
        buttonScan.setDisable(!isStop);
        buttonStop.setDisable(isStop);
        counter.set(0);
        executorService.execute(() -> {
            File disk = choiceBoxDisks.getValue();
            traversalDisk(disk);
            buttonScan.setDisable(isStop);
            buttonStop.setDisable(!isStop);
        });
    }

    @FXML
    public void onClickStop() {
        isStop = true;
        Platform.runLater(() -> {
            buttonScan.setDisable(!isStop);
            buttonStop.setDisable(isStop);
        });
    }

    private void traversalDisk(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File currentFile : files) {
                if (isStop) {
                    return;
                }
                if (currentFile.isDirectory()) {
                    traversalDisk(currentFile);
                } else {
                    Platform.runLater(() -> {
                        String value = String.format("%d | %s", counter.incrementAndGet(), currentFile.getAbsoluteFile());
                        labelStatus.set(value);
                    });
                }
            }
        }
    }
}
