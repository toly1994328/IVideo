package com.toly1994.ivideo.view.home.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:21:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public abstract class BasePopWindow extends PopupWindow {
    private View mRootView;
    private Context mContext;

    private SparseArray<View> mViews;

    public BasePopWindow(Context context) {
        this(context, -1);
    }


    public BasePopWindow(Context context, int animStyleId) {




        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(layoutId(), null);
        mViews = new SparseArray<>();
        //基本设置
        setContentView(mRootView);
        setWidth(150*3);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        //沉浸标题栏,全屏显示
        setClippingEnabled(false);
        setBackgroundDrawable(new BitmapDrawable());
        if (animStyleId != -1) {
            setAnimationStyle(animStyleId);//设置mPopWindow进出动画
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId pop里的控件id
     * @param <T>    控件视图
     * @return 控件视图
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public abstract int layoutId();
}