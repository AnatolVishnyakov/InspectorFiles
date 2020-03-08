package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.FolderSnapshotController;
import ru.inspector_files.controller.snapshot.ProcessController;
import ru.inspector_files.controller.snapshot.StopController;

public class SnapshotMediator implements SnapshotMediateControllers {
    private FolderSnapshotController folderSnapshotController;
    private ProcessController processController;
    private StopController stopController;

    private SnapshotMediator() {
    }

    public static SnapshotMediator getInstance() {
        return SnapshotMediatorHolder.INSTANCE;
    }

    private static class SnapshotMediatorHolder {
        private static final SnapshotMediator INSTANCE = new SnapshotMediator();
    }

    @Override
    public void registerScanController(FolderSnapshotController folderSnapshotController) {
        this.folderSnapshotController = folderSnapshotController;
    }

    @Override
    public void registerProcessController(ProcessController processController) {
        this.processController = processController;
    }

    @Override
    public void registerStopController(StopController stopController) {
        this.stopController = stopController;
    }
}
