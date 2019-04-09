package com.toly1994.ivideo.app.api;

import com.toly1994.ivideo.model.me.User;
import com.toly1994.ivideo.model.login.ResultBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:16:47<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface UserApi {

    /**
     * 查询所有操作
     *
     * @return ResultBean包装
     */
    @GET("api/user/")
    Observable<ResultBean> findAll();


    @GET("api/user/name")//http://localhost:8000/api/user/name?name=捷特
    Observable<ResultBean> findByName(@Query("name") String name);

    @GET("api/user/detail/{id}/")//http://localhost:8000/api/user/detail/1/
    Observable<User> getDetail(@Path("id") int id);
}
