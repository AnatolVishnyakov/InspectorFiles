package ru.inspector_files.repository;

import ru.inspector_files.model.Document;

public interface DocumentRepository {
    Document save(Document document);
}
