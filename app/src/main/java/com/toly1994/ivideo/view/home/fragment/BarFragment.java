package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.toly1994.ivideo.R;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:11:25<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class BarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.in_pager_home_appbar_top, container, false);
        return view;
    }
}
