package com.toly1994.ivideo.app.utils.filer;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：张风捷特烈
 * 时间：2019/2/13/013:8:56
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Filer {
    private File file;//文件夹名
    private int dirCount = 1;//子文件夹数量---不包括自身
    private int fileCount;//文件的个数
    private long length; //文件夹大小
    //过滤器集合
    private transient ArrayList<FileFilter> mFilters = new ArrayList<>();

    public int curDeep;//节点深度

    public void addFilter(FileFilter countFilter) {
        mFilters.add(countFilter);
    }

    public File getFile() {
        return file;
    }

    public int getDirCount() {
        return dirCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public long getLength() {
        return length;
    }


    private transient FileNode root;//根节点

    public Filer(String rootPath) {
        file = new File(rootPath);
        root = new FileNode(file);
    }

    public void scan() {
        long start = System.currentTimeMillis();
        scan(root);
        System.out.println("scan耗时-----" + (System.currentTimeMillis() - start) / 1000.f + "秒");
    }


    private void scan(FileNode node) {
        File file = node.file;
        if (file.isFile()) {//如果节点是文件
            return;
        }

        File[] files = file.listFiles();
        for (File f : files) {
            FileNode child = new FileNode(f);
            child.deep = curDeep;
            node.child.add(child);

            for (FileFilter filter : mFilters) {
                if (filter != null && filter.iCanGo(f)) {
                    filter.filter(child.file, child.deep);
                }
            }

            if (f.isDirectory()) {
                dirCount++;//每调用一次说明有一个文件夹
                curDeep++;
                scan(child);
            } else {
                fileCount++;//每调用一次说明有一个文件
                length += f.length();
            }
        }
        curDeep--;
    }

    /**
     * 文件节点
     */
    private class FileNode {
        public ArrayList<FileNode> child;//子节点集合
        public File file; //文件路径
        public int deep;//深度

        public FileNode(File file) {
            this.file = file;
            child = new ArrayList<>(4);
        }
    }

    @Override
    public String toString() {
        return "Filer{" +
                "file=" + file +
                ", dirCount=" + dirCount +
                ", fileCount=" + fileCount +
                ", length=" + length +
                ", curDeep=" + curDeep +
                '}';
    }

    /**
     * 获取文件的lever个父目录字符串
     *
     * @param file  文件
     * @param lever 层级
     * @return
     */
    public static String getParent(File file, int lever) {
        StringBuilder sb = new StringBuilder();

        String path = file.getAbsolutePath();

        String[] split = path.split(File.separator);

        if (split.length < lever) {
            return path.substring(0, path.lastIndexOf(File.separator) + 1);
        } else {
            int start = split.length - lever - 1;
            for (int i = start; i < start + lever; i++) {
                String result = split[i];
                sb.append(result).append(File.separator);
            }
        }
        return sb.toString();
    }

    public static String getParent(String path, int lever) {
        return getParent(new File(path), lever);
    }
}
