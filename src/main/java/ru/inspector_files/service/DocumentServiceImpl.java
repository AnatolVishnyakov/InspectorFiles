package ru.inspector_files.service;

import ru.inspector_files.model.Document;
import ru.inspector_files.repository.DocumentRepository;
import ru.inspector_files.repository.DocumentRepositoryImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository repository = new DocumentRepositoryImpl();

    @Override
    public Document create(File file) {
        Document document = new Document();
        document.setName(file.getName());
        document.setPath(file.getParent());
        document.setSize(file.length());
        document.setCheckSum(calculateCheckSum(file));
        document.setCreateTime(LocalDateTime.now());
        document.setContentType(calculateContentType(file));
        return repository.save(document);
    }

    private String calculateContentType(File file) {
        try {
            return Files.probeContentType(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            return "Don't processing content type file!";
        }
    }

    private String calculateCheckSum(File file) {
        Checksum checksum = new CRC32();
        byte[] buffer = new byte[1024];

        try (InputStream fis = Files.newInputStream(Paths.get(file.getAbsolutePath()))) {
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                checksum.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return "File processing error!";
        }
        return String.valueOf(checksum.getValue());
    }
}
