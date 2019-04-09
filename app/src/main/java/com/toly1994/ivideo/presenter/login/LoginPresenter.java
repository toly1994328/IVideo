package com.toly1994.ivideo.presenter.login;

import android.content.Intent;
import com.toly1994.ivideo.app.Cons;
import com.toly1994.ivideo.app.utils.SpUtils;
import com.toly1994.ivideo.model.login.Callback;
import com.toly1994.ivideo.model.login.ResultBean;
import com.toly1994.ivideo.model.login.UserModel;
import com.toly1994.ivideo.view.home.HomeView;
import com.toly1994.ivideo.view.login.ILoginView;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:17:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class LoginPresenter {

    private final UserModel mModel;
    private ILoginView mLoginView;

    public LoginPresenter(ILoginView loginView) {
        mLoginView = loginView;
        mModel = new UserModel();

    }

    public void validate(String user, String pwd) {
        mModel.findByName(user, new Callback<ResultBean.DataBean>() {
            @Override
            public void onStartLoad() {
                mLoginView.showLoading();
            }

            @Override
            public void onSuccess(ResultBean.DataBean data) {
                if (data.getName().equals(user) && data.getPwd().equals(pwd)) {
                    Intent intent = new Intent(mLoginView.getCtx(), HomeView.class);
                    intent.putExtra("login_ok", "login_ok");
                    mLoginView.getCtx().startActivity(intent);

                    SpUtils.newInstance().setString(Cons.SP_USER, user);
                    SpUtils.newInstance().setInt(Cons.SP_USER_ID, data.getId());
                    mLoginView.finish();
                }
                mLoginView.hideLoading();

            }

            @Override
            public void onError(Throwable e) {
                mLoginView.showError(e.getMessage());
                mLoginView.hideLoading();
            }
        });

    }
}
