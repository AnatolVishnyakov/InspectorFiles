package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.FolderProcessComponentController;
import ru.inspector_files.controller.snapshot.FolderProcessController;
import ru.inspector_files.controller.snapshot.FolderSnapshotController;
import ru.inspector_files.controller.snapshot.PanelContent;

public interface SnapshotMediateControllers {
    void registerScanController(FolderSnapshotController folderSnapshotController);

    void registerProcessController(FolderProcessComponentController folderProcessComponentController);

    void registerStopController(FolderProcessController folderProcessController);

    <T extends PanelContent> Object getUserData(Class<T> clazz);
}
