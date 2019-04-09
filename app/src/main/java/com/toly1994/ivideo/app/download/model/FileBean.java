package com.toly1994.ivideo.app.download.model;

import java.io.Serializable;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:12:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：文件信息封装类
 */
public class FileBean implements Serializable {
    private int id;//文件id
    private String url;//文件下载地址
    private String fileName;//文件名
    private long length;//文件长度
    private long loadedLen;//文件已下载长度

    public FileBean() {
    }

    public FileBean(int id, String url, String fileName, long length, long loadedLen) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.loadedLen = loadedLen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLoadedLen() {
        return loadedLen;
    }

    public void setLoadedLen(long loadedLen) {
        this.loadedLen = loadedLen;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", loadedLen=" + loadedLen +
                '}';
    }
}
