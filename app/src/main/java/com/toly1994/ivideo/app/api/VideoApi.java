package com.toly1994.ivideo.app.api;

import com.toly1994.ivideo.model.me.VideoResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:16:47<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface VideoApi {

    @GET("api/user/{id}/video/")//http://192.168.10.114:8000/api/user/1/video/
    Observable<VideoResult> getUserVideo(@Path("id") int id);
}
