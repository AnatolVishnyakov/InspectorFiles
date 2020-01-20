package ru.inspectorfiles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "file")
public class Document {
    @NotBlank
    @Column(name = "absolute_path", nullable = false, unique = true)
    private String path;
    @Column(name = "file_name")
    private String name;
    @Column(name = "size")
    private int size;
    @Column(name = "check_sum")
    private String checkSum;

    public Document() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    @Override
    public String toString() {
        return "Document{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", checkSum='" + checkSum + '\'' +
                '}';
    }
}
