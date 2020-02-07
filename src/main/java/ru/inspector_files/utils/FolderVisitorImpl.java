package ru.inspector_files.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderVisitorImpl implements FileVisitor<File> {
    @Override
    public FileVisitResult preVisitDirectory(File dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("preVisitDirectory");
        return null;
    }

    @Override
    public FileVisitResult visitFile(File file, BasicFileAttributes attrs) throws IOException {
        System.out.println("visitFile");
        return null;
    }

    @Override
    public FileVisitResult visitFileFailed(File file, IOException exc) throws IOException {
        System.out.println("visitFileFailed");
        return null;
    }

    @Override
    public FileVisitResult postVisitDirectory(File dir, IOException exc) throws IOException {
        System.out.println("postVisitDirectory");
        return null;
    }
}
