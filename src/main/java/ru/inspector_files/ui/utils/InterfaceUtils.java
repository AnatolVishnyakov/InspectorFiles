package ru.inspector_files.ui.utils;

import javafx.application.Platform;
import ru.inspector_files.ui.ExecutableUI;

public final class InterfaceUtils {
    private InterfaceUtils() {
    }

    public static void updateElement(ExecutableUI code) {
        Platform.runLater(code::execute);
    }
}
