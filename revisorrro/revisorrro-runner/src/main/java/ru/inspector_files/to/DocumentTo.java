package ru.inspector_files.to;

import javafx.beans.property.*;
import ru.inspector_files.model.Document;

import java.time.LocalDateTime;

public class DocumentTo {
    private StringProperty absolutePath;
    private StringProperty fileName;
    private LongProperty fileSize;
    private StringProperty checkSum;
    private ObjectProperty<LocalDateTime> createTime;
    private StringProperty contentType;
    private IntegerProperty level;

    public DocumentTo() {
        this.absolutePath = new SimpleStringProperty();
        this.fileName = new SimpleStringProperty();
        this.fileSize = new SimpleLongProperty();
        this.checkSum = new SimpleStringProperty();
        this.createTime = new SimpleObjectProperty<>();
        this.contentType = new SimpleStringProperty();
        this.level = new SimpleIntegerProperty();
    }

    public DocumentTo(Document document) {
        this();
        this.absolutePath.set(document.getPath());
        this.fileName.set(document.getName());
        this.fileSize.set(document.getSize());
        this.checkSum.set(document.getCheckSum());
        this.createTime.set(document.getCreateTime());
        this.contentType.set(document.getContentType());
        this.level.set(document.getLevel());
    }

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

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }
}
