package ru.itis.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Builder
@ToString
public class FileInfo {
    private Integer id;
    private String originalFileName;
    private Integer storageFileNameByUserId;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Integer getStorageFileNameByUserId() {
        return storageFileNameByUserId;
    }

    public void setStorageFileNameByUserId(Integer storageFileNameByUserId) {
        this.storageFileNameByUserId = storageFileNameByUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
