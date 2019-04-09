package com.toly1994.ivideo.model.me;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/8/008:7:32<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class User {
    public boolean isLogin;

    /**
     * code : 200
     * data : {"video_count":2,"name":"捷特","icon":"捷特/捷特.png","info":"我什么也没留下"}
     * msg : 操作成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public static User objectFromData(String str) {

        return new Gson().fromJson(str, User.class);
    }

    public static List<User> arrayUserFromData(String str) {

        Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * video_count : 2
         * name : 捷特
         * icon : 捷特/捷特.png
         * info : 我什么也没留下
         */

        private int video_count;
        private String name;
        private String icon;
        private String info;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getVideo_count() {
            return video_count;
        }

        public void setVideo_count(int video_count) {
            this.video_count = video_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
