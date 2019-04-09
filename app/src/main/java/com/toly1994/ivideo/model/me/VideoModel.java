package com.toly1994.ivideo.model.me;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.toly1994.ivideo.app.Cons;
import com.toly1994.ivideo.app.api.VideoApi;
import com.toly1994.ivideo.app.utils.SpUtils;
import com.toly1994.ivideo.model.login.Callback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:16:56<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class VideoModel {
    private final VideoApi mApi;

    public VideoModel() {
        mApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Cons.BASE_URL)
                .build().create(VideoApi.class);
    }


    public void getVideos(Callback<List<VideoResult.DataBean>> callback) {

        int id = SpUtils.newInstance().getInt(Cons.SP_USER_ID);
        if (id == 0) {
            if (callback != null) {
                callback.onError(null);
            }
        }

        mApi.getUserVideo(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (callback != null) {
                            callback.onStartLoad();
                        }
                    }

                    @Override
                    public void onNext(VideoResult result) {
                        if (callback != null) {
                            callback.onSuccess(result.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
