package com.toly1994.ivideo.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/8/008:12:43<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：视频播放：MediaPlayer + SurfaceView + MediaController
 */
public class VideoView extends SurfaceView implements MediaController.MediaPlayerControl {
    private static final String TAG = "VideoView";
    private SurfaceHolder mSurfaceHolder;//SurfaceHolder
    private MediaPlayer mMediaPlayer;//媒体播放器

    private int mVideoHeight;//视频宽高
    private int mVideoWidth;//视频高
    private int mSurfaceHeight;//SurfaceView高
    private int mSurfaceWidth;//SurfaceView宽

    private boolean isPrepared;//是否已准备好
    private Uri mUri;//播放的地址
    private int mCurrentPos;//当前进度
    private int mDuration = -1;//当前播放视频时长
    private int mCurrentBufferPer;//当前缓冲进度--网络
    private Timer mTimer;

    private String path;//视频路径

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTimer = new Timer();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;
                openVideo();
                updateProgress();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//此处回调width, height为View尺寸大小
                mSurfaceHeight = height;
                mSurfaceWidth = width;
                if (mMediaPlayer != null && isPrepared) {
                    initPosition();
                    mMediaPlayer.start();//开始播放

                    //开启面板
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceHolder = null;
                //隐藏面板
                releasePlayer();
                mTimer.cancel();
            }
        });
    }


    /**
     * 初始化最初位置
     */
    private void initPosition() {
        if (mCurrentPos != 0) {
            mMediaPlayer.seekTo(mCurrentPos);
            mCurrentPos = 0;
        }
    }

    private void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            return;
        }
        isPrepared = false;//没有准备完成
        releasePlayer();
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(getContext(), mUri);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);//播放时屏幕一直亮着
            mMediaPlayer.prepareAsync();//异步准备
        } catch (IOException e) {
            e.printStackTrace();
        }

        //尺寸改变监听
        mMediaPlayer.setOnVideoSizeChangedListener((mp, width, height) -> {//此处回调width, height为视频尺寸大小
            mVideoWidth = width;
            mVideoHeight = height;
            if (mOnSizeChanged != null) {
                mOnSizeChanged.onSizeChange();
            }
            if (mVideoWidth != 0 && mVideoHeight != 0) {
//                changeVideoSize(1, 1);
//                fitVideoSize();
//                fitSize16_9();
//                fitSize(4.f / 3);
                fitVideoSize(mVideoWidth, mVideoHeight, mSurfaceWidth, mSurfaceHeight);
            }
        });


        //准备监听
        mMediaPlayer.setOnPreparedListener(mp -> {
            isPrepared = true;
            //控制器可用
            if (mOnPreparedListener != null) {//补偿回调
                mOnPreparedListener.onPrepared(mp);
            }
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                //开始初始化
                initPosition();
                start();
            }
        });

        //完成监听
        mMediaPlayer.setOnCompletionListener(mp -> {
            //隐藏面板
            start();
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mp);
            }
        });
        //错误监听
        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            //隐藏面板
            if (mOnErrorListener != null) {
                mOnErrorListener.onError(mp, what, extra);
            }
            return true;
        });


        mMediaPlayer.setOnBufferingUpdateListener((mp, pre) -> {
            mCurrentBufferPer = pre;
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.update(pre);
            }

        });
    }


    public void fitVideoSize() {
        float rateY = 1;//高不变
        float rateX = mVideoWidth * 1.f / mVideoHeight;
        changeVideoFitSize(mVideoWidth, mVideoHeight, mSurfaceWidth, mSurfaceHeight, rateX, rateY);
    }


    public void fitSize16_9() {
        float rateY = 1;//高不变
        float rate = 16.f / 9;

        float W = rate * mVideoHeight;
        float rateX = W / mVideoWidth;//高不变
        //W:H =16:9 ------ W= 16/9*H

        changeVideoFitSize(mVideoWidth, mVideoHeight, mSurfaceWidth, mSurfaceHeight, rateX, rateY);
    }

    public void fitSize(float rate) {

        Log.e(TAG, "fitSize: " + mSurfaceWidth * 1.0f / mSurfaceHeight);
        Log.e(TAG, "fitSize: " + rate);

        float judge = mSurfaceWidth * 1.0f / mSurfaceHeight - rate;
        if (Math.abs(judge) < 0.005) {
            return;
        }

        float rateY = 1;//高不变
        //W:H =4:3 ------ W= 4/3*H
        float W = rate * mSurfaceHeight;
        float rateX = W / mSurfaceWidth;//高不变
        changeVideoFitSize(mVideoWidth, mVideoHeight, mSurfaceWidth, mSurfaceHeight, rateX, rateY);
    }


    public void changeVideoSize(float rateX, float rateY) {
        changeVideoFitSize(mVideoWidth, mVideoHeight, mSurfaceWidth, mSurfaceHeight, rateX, rateY);
    }

    public void fitVideoSize(
            int videoW, int videoH, int surfaceW, int surfaceH) {


        float ratio = videoW * 1.f / videoH;

        //surfaceW:surfaceH=videoW:videoH --> surfaceW=videoW:videoH*surfaceH

        surfaceW = (int) (ratio * surfaceH);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(surfaceW, surfaceH);
        params.addRule(13);
        setLayoutParams(params);
    }

    /**
     * @deprecated 暂时还没规划好 先用  {@link VideoView#fitVideoSize(int, int, int, int)}
     */
    @Deprecated
    public void changeVideoFitSize(
            int videoW, int videoH, int surfaceW, int surfaceH,
            float rateX, float rateY) {
        float videoSizeRate = videoW * 1.0f / videoH;


        //横屏下的切换 -- 正常宽高比例
        float widthRatePortrait = videoW * 1.0f / surfaceW;
        float heightRatePortrait = videoH * 1.0f / surfaceH;


        //横屏下的切换 View宽高互换-- 宽高比例
        float widthRateLand = videoW * 1.0f / surfaceH;
        float heightRateLand = videoH * 1.0f / surfaceW;

        float ratio;
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {//横屏
            //竖屏模式下
            ratio = Math.max(widthRatePortrait, heightRatePortrait);
        } else {
            //横屏模式下
            if (videoSizeRate > 1) {//横向视频
                ratio = Math.min(widthRatePortrait, widthRatePortrait);
            } else {//竖向视频
                ratio = Math.max(widthRateLand, heightRateLand);
            }
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoW = (int) Math.ceil(videoW * 1.0f / ratio * rateX);
        videoH = (int) Math.ceil(videoH * 1.0f / ratio * rateY);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(videoW, videoH);
        params.addRule(13);
        setLayoutParams(params);
    }


    /**
     * 变速
     *
     * @param speed
     */
    public void changeSpeed(float speed) {
        //API 23 + 支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.setPlaybackParams(mMediaPlayer.getPlaybackParams().setSpeed(speed));
            } else {
                mMediaPlayer.setPlaybackParams(mMediaPlayer.getPlaybackParams().setSpeed(speed));
                mMediaPlayer.pause();
            }
        }
    }

    /**
     * 释放播放器
     */
    private void releasePlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    public void setVideoPath(String path) {
        this.path = path;
        mUri = Uri.parse(path);
        setVideoURI(mUri);
    }

    public void setVideoURI(Uri uri) {
        mUri = uri;
        mCurrentPos = 0;
        openVideo();//打开视频
        requestLayout();//更新界面
        invalidate();
    }


    public void stopPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private boolean canPlay() {
        return mMediaPlayer != null && isPrepared;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = adjustSize(mVideoWidth, widthMeasureSpec);
        int h = adjustSize(mVideoHeight, heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    public int adjustSize(int size, int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int len = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, len);
                break;
            case MeasureSpec.EXACTLY:
                result = len;
                break;
        }
        return result;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return new File(path).getName();
    }

    //----------------------------------------------------------------
    //------------MediaPlayerControl接口函数---------------------------
    //----------------------------------------------------------------
    @Override
    public void start() {
        if (canPlay()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (canPlay() && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public int getDuration() {
        if (canPlay()) {
            if (mDuration > 0) {
                return mDuration;
            }
            mDuration = mMediaPlayer.getDuration();
            return mDuration;
        }
        mDuration = -1;
        return mDuration;
    }

    @Override
    public int getCurrentPosition() {
        if (canPlay()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        pause();
        mMediaPlayer.seekTo((int) (pos / 100.f * getDuration()));
        start();
    }

    @Override
    public boolean isPlaying() {
        if (canPlay()) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public int getBufferPercentage() {
        if (canPlay()) {
            return mCurrentBufferPer;
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


    public void togglePlay(ImageView imageView, int start, int stop) {
        if (isPlaying()) {
            imageView.setImageResource(start);
            pause();
        } else {
            imageView.setImageResource(stop);
            start();
        }

    }


    private void updateProgress() {
        mTimer.schedule(new TimerTask() {
            public void run() {
                int duration = mMediaPlayer.getDuration();//获取总时长
                int currentPosition = mMediaPlayer.getCurrentPosition();//获取当前播放了多少
                int per_100 = (int) (currentPosition * 1.0 / duration * 100);
                if (mOnProgressChanged != null) {
                    post(() -> mOnProgressChanged.onChange(per_100));
                }
            }
        }, 0, 1000);
    }

    //----------------------------------------------------------------
    //------------补偿回调---------------------------
    //----------------------------------------------------------------
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    public interface OnSizeChanged {
        void onSizeChange();
    }

    private OnSizeChanged mOnSizeChanged;

    public void setOnSizeChanged(OnSizeChanged onSizeChanged) {
        mOnSizeChanged = onSizeChanged;
    }

    public interface OnProgressChanged {
        void onChange(int per_100);
    }

    private OnProgressChanged mOnProgressChanged;

    public void setOnProgressChanged(OnProgressChanged onProgressChanged) {
        mOnProgressChanged = onProgressChanged;
    }

    public interface OnBufferingUpdateListener {
        void update(int pre);
    }

    private OnBufferingUpdateListener mOnBufferingUpdateListener;

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

}


