package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.ProcessController;
import ru.inspector_files.controller.snapshot.ScanController;
import ru.inspector_files.controller.snapshot.StopController;

public interface SnapshotMediateControllers {
    void registerScanController(ScanController scanController);

    void registerProcessController(ProcessController processController);

    void registerStopController(StopController stopController);
}
