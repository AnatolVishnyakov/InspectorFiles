package ru.inspector_files.to;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class DocumentTo {
    private StringProperty absolutePath;
    private StringProperty fileName;
    private LongProperty fileSize;
    private StringProperty checkSum;
    private ObjectProperty<LocalDateTime> createTime;
    private StringProperty contentType;

    public String getAbsolutePath() {
        return absolutePath.get();
    }

    public StringProperty absolutePathProperty() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath.set(absolutePath);
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public long getFileSize() {
        return fileSize.get();
    }

    public LongProperty fileSizeProperty() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize.set(fileSize);
    }

    public String getCheckSum() {
        return checkSum.get();
    }

    public StringProperty checkSumProperty() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum.set(checkSum);
    }

    public String getContentType() {
        return contentType.get();
    }

    public StringProperty contentTypeProperty() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType.set(contentType);
    }

    public LocalDateTime getCreateTime() {
        return createTime.get();
    }

    public ObjectProperty<LocalDateTime> createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime.set(createTime);
    }
}
