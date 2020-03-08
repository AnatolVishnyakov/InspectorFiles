package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.ProcessController;
import ru.inspector_files.controller.snapshot.ScanController;
import ru.inspector_files.controller.snapshot.StopController;

public class SnapshotMediator implements SnapshotMediateControllers {
    private ScanController scanController;
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
    public void registerScanController(ScanController scanController) {
        this.scanController = scanController;
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
