package ru.inspector_files.repository;

import ru.inspector_files.model.Document;

import java.util.List;

public interface DocumentRepository {
    Document save(Document document);

    List<Document> getDocumentByLevel(int level);
}
