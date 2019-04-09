package com.toly1994.ivideo.model.login;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.toly1994.ivideo.app.Cons;
import com.toly1994.ivideo.app.api.UserApi;
import com.toly1994.ivideo.app.utils.SpUtils;
import com.toly1994.ivideo.model.me.User;
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
public class UserModel {
    private final UserApi mNoteApi;

    public UserModel() {
        mNoteApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Cons.BASE_URL)
                .build().create(UserApi.class);
    }

    public void getData(Callback<List<ResultBean.DataBean>> callback) {
        mNoteApi.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (callback != null) {
                            callback.onSuccess(resultBean.getData());
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

    public void findByName(String name, Callback<ResultBean.DataBean> callback) {
        mNoteApi.findByName(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (callback != null) {
                            callback.onStartLoad();
                        }

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (callback != null) {
                            callback.onSuccess(resultBean.getData().get(0));
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

    public void getDetail(Callback<User> callback) {


        int id = SpUtils.newInstance().getInt(Cons.SP_USER_ID);
        if (id == 0) {
            if (callback != null) {
                User user = new User();
                user.isLogin = false;
                User.DataBean data = new User.DataBean();
                data.setName("点击登录");
                data.setInfo("我什么也没留下");
                data.setVideo_count(0);
                data.setIcon("default/icon_default.jpg");
                user.setData(data);
                callback.onSuccess(user);
            }
        }


        mNoteApi.getDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (callback != null) {
                            callback.onStartLoad();
                        }

                    }

                    @Override
                    public void onNext(User user) {
                        user.isLogin = true;
                        if (callback != null) {
                            callback.onSuccess(user);
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
