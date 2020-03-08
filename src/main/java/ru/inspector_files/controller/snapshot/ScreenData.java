package ru.inspector_files.controller.snapshot;

import javafx.scene.Parent;

public interface ScreenData {
    Object getUserData();

    void setUserData(Parent panel, Object userData);
}
