package com.toly1994.ivideo.widget;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:23:22<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/10/010:8:00<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：多图标切换器
 */
public class ToggleImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "ToggleImageView";
    private AnimatorSet mSet;//动画集合
    private List<Integer> mResIds;//图片资源id
    private List<OnClickListener> mListeners;
    private int mCurrentIdx;//当前位置
    private boolean isWithScale = true;//点击时是否略微缩放
    private Animator[] mAnimators;//自定义动画

    public ToggleImageView(Context context) {
        this(context, null);
    }

    public ToggleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResIds = new ArrayList<>();
        mListeners = new ArrayList<>();
        mSet = new AnimatorSet();
        //设置动画结束监听
        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setImageResource(mResIds.get(mCurrentIdx));
                if (mListeners.size() > 0) {
                    mListeners.get(mCurrentIdx).onClick(ToggleImageView.this);
                }
                if (mOnToggleListener != null) {
                    mOnToggleListener.toggle(ToggleImageView.this, mCurrentIdx);
                }
                mCurrentIdx++;
                if (mCurrentIdx == mResIds.size()) {
                    mCurrentIdx = 0;
                }
            }
        });
    }

    public void setAnimator(Animator... animators) {
        mAnimators = animators;
    }

    public void setResIds(List<Integer> resIds) {
        mResIds = resIds;
        setImageResource(mResIds.get(mCurrentIdx));
        mCurrentIdx = 1;
    }

    /**
     * 初始化双按钮
     *
     * @param resFrom      第一状态
     * @param callBackFrom 第一状态器
     * @param resTo        第二状态
     * @param callBackTo   第二状态器
     */
    public void initTwoButton(int resFrom, OnClickListener callBackFrom, int resTo, OnClickListener callBackTo) {
        mResIds.add(resFrom);
        mResIds.add(resTo);
        mListeners.add(callBackFrom);
        mListeners.add(callBackTo);
        setImageResource(mResIds.get(mCurrentIdx));
        mCurrentIdx = 1;
    }

    /**
     * 是否有缩放效果
     *
     * @param withScale 默认true
     */
    public void setWithScale(boolean withScale) {
        isWithScale = withScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, .1f, 1f).setDuration(300);
                mSet.play(alpha);
                if (isWithScale) {
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, .8f, 1f).setDuration(300);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, .8f, 1f).setDuration(300);
                    mSet.play(scaleX).with(scaleY);
                }
                mSet.playTogether(mAnimators);
                mSet.start();
                break;
        }
        return super.onTouchEvent(event);
    }

    //监听
    public interface OnToggleListener {
        void toggle(View view, int currId);
    }

    private OnToggleListener mOnToggleListener;

    public void setOnToggleListener(OnToggleListener onToggleListener) {
        mOnToggleListener = onToggleListener;
    }
}