package ru.inspector_files.to;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProgressComponent {
    private AtomicBoolean isRunning;
    private JFXProgressBar scanStatusProgressBar;
    private Label stateInMemoryLabel;
    private Label durationInPercentage;
    private Label folderPathLabel;

    public ProgressComponent() {
    }

    public JFXProgressBar getScanStatusProgressBar() {
        return scanStatusProgressBar;
    }

    public void setScanStatusProgressBar(JFXProgressBar scanStatusProgressBar) {
        this.scanStatusProgressBar = scanStatusProgressBar;
    }

    public Label getStateInMemoryLabel() {
        return stateInMemoryLabel;
    }

    public void setStateInMemoryLabel(Label stateInMemoryLabel) {
        this.stateInMemoryLabel = stateInMemoryLabel;
    }

    public Label getDurationInPercentage() {
        return durationInPercentage;
    }

    public void setDurationInPercentage(Label durationInPercentage) {
        this.durationInPercentage = durationInPercentage;
    }

    public Label getFolderPathLabel() {
        return folderPathLabel;
    }

    public void setFolderPathLabel(Label folderPathLabel) {
        this.folderPathLabel = folderPathLabel;
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }
}
