package ru.inspectorfiles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileInfo {
    private StringProperty fileName = new SimpleStringProperty();
    private IntegerProperty numberOfFiles = new SimpleIntegerProperty();

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public int getNumberOfFiles() {
        return numberOfFiles.get();
    }

    public IntegerProperty numberOfFilesProperty() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles.set(numberOfFiles);
    }
}
