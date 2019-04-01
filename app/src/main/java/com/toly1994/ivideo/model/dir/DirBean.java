package com.toly1994.ivideo.model.dir;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/17/017:13:00<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class DirBean {

    /**
     * 当前文件夹路径
     */
    private String dir;

    /**
     * 当前文件夹第一个照片的路径
     */
    private String firstImgPath;
    /**
     *
     */
    private String name;
    /**
     * 当前文件夹内图片数量
     */
    private int count;


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        String[] names = this.dir.split("/");
        this.name = names[names.length - 1];
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "FolderBean{" +
                "dir='" + dir + '\'' +
                ", firstImgPath='" + firstImgPath + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}