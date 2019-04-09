package com.toly1994.ivideo.app.download.model;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:12:20<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：线程信息封装类
 */
public class ThreadBean {
    private int id;//线程id
    private String url;//线程所下载文件的url
    private long start;//线程开始的下载位置
    private long end;//线程结束的下载位置
    private long loadedLen;//该线程已下载的长度

    public ThreadBean() {
    }

    public ThreadBean(int id, String url, long start, long end, long loadedLen) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getLoadedLen() {
        return loadedLen;
    }

    public void setLoadedLen(long loadedLen) {
        this.loadedLen = loadedLen;
    }

    @Override
    public String toString() {
        return "ThreadBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", loadedLen=" + loadedLen +
                '}';
    }
}
