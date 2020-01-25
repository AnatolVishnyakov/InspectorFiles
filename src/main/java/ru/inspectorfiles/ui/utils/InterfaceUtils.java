package ru.inspectorfiles.ui.utils;

import javafx.application.Platform;
import ru.inspectorfiles.ui.ExecutableUI;

public final class InterfaceUtils {
    private InterfaceUtils() {
    }

    public static void updateElement(ExecutableUI code) {
        Platform.runLater(code::execute);
    }
}
