package ru.inspector_files.service;

import ru.inspector_files.model.Document;

import java.io.File;
import java.util.List;

public interface DocumentService {
    Document create(File file);

    List<Document> getAllRootLevel();
}
