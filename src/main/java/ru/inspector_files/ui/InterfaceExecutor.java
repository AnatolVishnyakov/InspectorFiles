package ru.inspector_files.ui;

import javafx.application.Platform;

public final class InterfaceExecutor {
    private InterfaceExecutor() {
    }

    public static void execute(Runnable code) {
        Platform.runLater(code::run);
    }
}
