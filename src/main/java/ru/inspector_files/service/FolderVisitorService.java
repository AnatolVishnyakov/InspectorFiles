package ru.inspector_files.service;

import java.io.File;

public interface FolderVisitorService {
    void walk(File folder);
}
