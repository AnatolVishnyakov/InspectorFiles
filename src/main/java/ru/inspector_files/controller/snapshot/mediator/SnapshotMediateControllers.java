package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.ProcessController;
import ru.inspector_files.controller.snapshot.FolderSnapshotController;
import ru.inspector_files.controller.snapshot.StopController;

public interface SnapshotMediateControllers {
    void registerScanController(FolderSnapshotController folderSnapshotController);

    void registerProcessController(ProcessController processController);

    void registerStopController(StopController stopController);
}
