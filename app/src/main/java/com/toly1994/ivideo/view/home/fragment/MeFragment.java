package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.Cons;
import com.toly1994.ivideo.app.transform.PathTransformation;
import com.toly1994.ivideo.model.MeItem;
import com.toly1994.ivideo.model.me.User;
import com.toly1994.ivideo.presenter.IMePresenter;
import com.toly1994.ivideo.presenter.MePresenter;
import com.toly1994.ivideo.view.IMeView;
import com.toly1994.ivideo.view.home.adapter.MeCenterAdapter;
import com.toly1994.ivideo.view.home.adapter.MeTopAdapter;
import com.toly1994.ivideo.view.login.LoginView;

import java.util.ArrayList;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:10:28<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class MeFragment extends Fragment implements IMeView {
    private View mButton;
    private Context mContext;
    private ImageView mIcon;
    private RecyclerView mTopRv;
    private RecyclerView mCenterRv;
    private TextView mTvUser;
    private MePresenter mPresenter;
    private MeTopAdapter mTopAdapter;
    private ArrayList<MeItem> mMeItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = new MePresenter(this);
        View view = inflater.inflate(R.layout.fg_me, container, false);
        mContext = view.getContext();

        init(view);

        mPresenter.render(false);
        return view;
    }

    private void init(View view) {
        mButton = view.findViewById(R.id.id_login);
        mIcon = view.findViewById(R.id.id_iv_icon);
        mTopRv = view.findViewById(R.id.id_rv_info);
        mTvUser = view.findViewById(R.id.id_tv_login);

        mCenterRv = view.findViewById(R.id.id_rv_ability);


        mCenterRv.setLayoutManager(new LinearLayoutManager(mContext));
        MeCenterAdapter adapter = new MeCenterAdapter();
        mCenterRv.setAdapter(adapter);

        mTopRv.setLayoutManager(new GridLayoutManager(mContext, 4));

        mTopAdapter = new MeTopAdapter();
        mTopRv.setAdapter(mTopAdapter);

        mMeItems = new ArrayList<>();
        mMeItems.add(new MeItem(R.drawable.icon_video2, "我的视频", "0个视频"));
        mMeItems.add(new MeItem(R.drawable.icon_love, "我的收藏", "0个视频"));
        mMeItems.add(new MeItem(R.drawable.icon_factory, "视频工厂", "创意尽在DIY"));
        mMeItems.add(new MeItem(R.drawable.icon_download, "我的下载", "想看就看"));

    }


    @Override
    public void render(User user) {
        Picasso picasso = Picasso.get();
        RequestCreator load;
        if (user.isLogin) {
            mTvUser.setText(user.getData().getName());
            load = picasso.load(Cons.IMG_PREFIX + user.getData().getIcon());
            mMeItems.get(0).sub = user.getData().getVideo_count() + "个视频";
            mButton.setClickable(false);
        } else {
            load = picasso.load(R.mipmap.icon_default);
            mButton.setOnClickListener(v -> {
                    startActivity(new Intent(mContext, LoginView.class));
            });
        }

        mTopAdapter.setData(mMeItems);

        load.transform(new PathTransformation(true) {
            @Override
            protected Path formPath(int width, int height) {
                Path path = new Path();
                int size = Math.min(width, height);
                path.addCircle(size / 2, size / 2, size / 2, Path.Direction.CCW);
                return path;
            }
        }).into(mIcon);


    }

    @Override
    public IMePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
