package ru.inspector_files.service;

import java.io.File;
import java.util.Set;

public interface FolderVisitorService {
    void walk(Set<File> folders);
}
