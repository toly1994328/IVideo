package com.toly1994.ivideo.model.me;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/8/008:21:23<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class VideoResult {

    /**
     * code : 200
     * data : [{"name":"动漫音乐剪辑.mp4","type__type":"MV","size":52258704,"see":0},{"name":"游戏王mv.mp4","type__type":"MV","size":6556095,"see":0}]
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public static VideoResult objectFromData(String str) {

        return new Gson().fromJson(str, VideoResult.class);
    }

    public static List<VideoResult> arrayVideoResultFromData(String str) {

        Type listType = new TypeToken<ArrayList<VideoResult>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 动漫音乐剪辑.mp4
         * type__type : MV
         * size : 52258704
         * see : 0
         */

        private String name;
        private String type__type;
        private int size;
        private int see;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType__type() {
            return type__type;
        }

        public void setType__type(String type__type) {
            this.type__type = type__type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSee() {
            return see;
        }

        public void setSee(int see) {
            this.see = see;
        }
    }
}
