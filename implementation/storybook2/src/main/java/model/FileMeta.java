package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by liuwei on 2016-04-06.
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {

    private String fileName;
    private String fileSize;
    private String fileType;

    private byte[] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
