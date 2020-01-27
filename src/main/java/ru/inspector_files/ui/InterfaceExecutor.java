package ru.inspector_files.ui;

import javafx.application.Platform;

public final class InterfaceExecutor {
    private InterfaceExecutor() {
    }

    public static void execute(ExecutableUI code) {
        Platform.runLater(code::execute);
    }
}
