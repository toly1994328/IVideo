package com.toly1994.ivideo.model.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:16:49<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class ResultBean {

    /**
     * code : 200
     * data : [{"id":1,"name":"捷特","pwd":"1994","user_detail_id":1},{"id":2,"name":"龙少","pwd":"5d3447e8226a7f8458e61259618ae69d","user_detail_id":2},{"id":5,"name":"龙少2","pwd":"5d3447e8226a7f8458e61259618ae69d","user_detail_id":5},{"id":10,"name":"龙少22","pwd":"5d3447e8226a7f8458e61259618ae69d","user_detail_id":10},{"id":13,"name":"龙少221","pwd":"5d3447e8226a7f8458e61259618ae69d","user_detail_id":13}]
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public static ResultBean objectFromData(String str) {

        return new Gson().fromJson(str, ResultBean.class);
    }

    public static List<ResultBean> arrayResultBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ResultBean>>() {
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
         * id : 1
         * name : 捷特
         * pwd : 1994
         * user_detail_id : 1
         */

        private int id;
        private String name;
        private String pwd;
        private int user_detail_id;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public int getUser_detail_id() {
            return user_detail_id;
        }

        public void setUser_detail_id(int user_detail_id) {
            this.user_detail_id = user_detail_id;
        }
    }
}
