package com.toly1994.ivideo.presenter;

import com.toly1994.ivideo.model.login.Callback;
import com.toly1994.ivideo.model.login.UserModel;
import com.toly1994.ivideo.model.me.User;
import com.toly1994.ivideo.view.IMeView;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/8/008:14:35<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class MePresenter implements IMePresenter {


    private IMeView mView;
    private final UserModel mUserModel;

    public MePresenter(IMeView view) {

        mView = view;
        mUserModel = new UserModel();
    }

    @Override
    public void render(boolean force) {
//        User user = new User();
//        user.name = SpUtils.newInstance().getString(DownloadCons.SP_USER);
//        user.isLogin = !"".equals(user.name);

        mUserModel.getDetail(new Callback<User>() {
            @Override
            public void onStartLoad() {
                User user = new User();
                user.isLogin = false;
                mView.render(user);
            }

            @Override
            public void onSuccess(User user) {
                mView.render(user);
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }
}
