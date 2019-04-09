package com.toly1994.ivideo.view.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.utils.ScreenUtils;
import com.toly1994.ivideo.presenter.login.LoginPresenter;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/6/006:21:10<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class LoginView extends AppCompatActivity implements ILoginView {
    private static final String TAG = "LoginView";
    private LoginPresenter mLoginPresenter;
    private EditText mPwd;
    private EditText mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);


        setContentView(R.layout.activity_login);

        mPwd = findViewById(R.id.et_pw);
        mUsername = findViewById(R.id.et_username);


        mLoginPresenter = new LoginPresenter(this);

        findViewById(R.id.btn_register).setOnClickListener(v->{
            mLoginPresenter.validate(mUsername.getText().toString(),mPwd.getText().toString());
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getCtx() {
        return this;
    }
}
