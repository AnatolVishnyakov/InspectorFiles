package ru.inspector_files.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name = Document.GET_BY_LEVEL, query = "SELECT fs FROM Document fs WHERE fs.level = :level ORDER BY fs.path")
})
@Entity
@Table(name = "file_storage")
public class Document {
    public static final String GET_BY_LEVEL = "GetDocumentByLevel";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "absolute_path", nullable = false, length = 100_000)
    private String path;
    @Column
    private String name;
    @Column
    private long size;
    @Column(name = "check_sum")
    private String checkSum;
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    @Column(name = "content_type")
    private String contentType;
    @Column
    private int level;

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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public boolean isNew() {
        return id == null;
    }
}
