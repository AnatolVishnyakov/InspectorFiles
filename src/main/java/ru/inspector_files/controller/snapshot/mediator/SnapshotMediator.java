package ru.inspector_files.controller.snapshot.mediator;

import ru.inspector_files.controller.snapshot.FolderProcessComponentController;
import ru.inspector_files.controller.snapshot.FolderProcessController;
import ru.inspector_files.controller.snapshot.FolderSnapshotController;
import ru.inspector_files.controller.snapshot.ScreenData;

public class SnapshotMediator implements SnapshotMediateControllers {
    private FolderSnapshotController folderSnapshotController;
    private FolderProcessComponentController folderProcessComponentController;
    private FolderProcessController folderProcessController;

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
    public void registerProcessController(FolderProcessComponentController folderProcessComponentController) {
        this.folderProcessComponentController = folderProcessComponentController;
    }

    @Override
    public void registerStopController(FolderProcessController folderProcessController) {
        this.folderProcessController = folderProcessController;
    }

    @Override
    public <T extends ScreenData> Object getUserData(Class<T> clazz) {
        if (clazz.isInstance(folderSnapshotController)) {
            return folderSnapshotController.getUserData();
        } else if (clazz.isInstance(folderProcessController)) {
            return folderProcessController.getUserData();
        }
        throw new UnsupportedOperationException("Не поддерживаемый тип контроллера");
    }
}