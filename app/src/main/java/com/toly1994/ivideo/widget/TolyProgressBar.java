package com.toly1994.ivideo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.toly1994.ivideo.R;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/8 0008:12:37<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 * 默认：
 * 字体大小：10sp           文字颜色：0xff525252         文字间距：10dp
 * 背景颜色 0xffC9C9C9      进度条颜色：0xff54F340       进度条背景高：2dp 进度条高：2dp
 */
public class TolyProgressBar extends ProgressBar {

    protected Paint mPaint;
    private int mPBWidth;
    private RectF mRectF;
    protected Path mPath;
    private float[] mFloat8Left;
    private float[] mFloat8Right;

    private float mProgressX;//进度理论值
    private float mEndX;//进度条尾部
    private int mTextWidth;//文字宽度
    private boolean mLostRight;//是否不画右边
    private String mText;//文字


    protected int mPbBgColor = 0xffC9C9C9;
    protected int mPbOnColor = 0xff54F340;
    protected int mPbOnHeight = dp(6);
    protected int mPbBgHeight = dp(6);
    protected int mPbTxtColor = 0xff525252;
    protected int mPbTxtSize = sp(10);
    protected int mPbTxtOffset = sp(10);
    protected boolean mPbTxtGone = false;


    public TolyProgressBar(Context context) {
        this(context, null);
    }

    public TolyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TolyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TolyProgressBar);
        mPbOnHeight = (int) a.getDimension(R.styleable.TolyProgressBar_z_pb_on_height, mPbOnHeight);
        mPbTxtOffset = (int) a.getDimension(R.styleable.TolyProgressBar_z_pb_txt_offset, mPbTxtOffset);
        mPbOnColor = a.getColor(R.styleable.TolyProgressBar_z_pb_on_color, mPbOnColor);
        mPbTxtSize = (int) a.getDimension(R.styleable.TolyProgressBar_z_pb_txt_size, mPbTxtSize);
        mPbTxtColor = a.getColor(R.styleable.TolyProgressBar_z_pb_txt_color, mPbTxtColor);
        mPbBgHeight = (int) a.getDimension(R.styleable.TolyProgressBar_z_pb_bg_height, mPbBgHeight);
        mPbBgColor = a.getColor(R.styleable.TolyProgressBar_z_pb_bg_color, mPbBgColor);
        mPbTxtGone = a.getBoolean(R.styleable.TolyProgressBar_z_pb_txt_gone, mPbTxtGone);
        a.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mPbTxtSize);
        mPaint.setColor(mPbOnColor);
        mPaint.setStrokeWidth(mPbOnHeight);

        mRectF = new RectF();
        mPath = new Path();


        mFloat8Left = new float[]{//仅左边两个圆角--为背景
                mPbOnHeight / 2, mPbOnHeight / 2,//左上圆角x,y
                0, 0,//右上圆角x,y
                0, 0,//右下圆角x,y
                mPbOnHeight / 2, mPbOnHeight / 2//左下圆角x,y
        };

        mFloat8Right = new float[]{
                0, 0,//左上圆角x,y
                mPbBgHeight / 2, mPbBgHeight / 2,//右上圆角x,y
                mPbBgHeight / 2, mPbBgHeight / 2,//右下圆角x,y
                0, 0//左下圆角x,y
        };

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mPBWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();//进度条实际宽度
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        parseBeforeDraw();//绘制前对数值进行计算以及控制的flag设置

        if (getProgress() == 100) {//进度达到100后文字消失
            whenOver();
        }

        if (mEndX > 0) {//当进度条尾部>0绘制
            drawProgress(canvas);
        }


        if (!mPbTxtGone) {//绘制文字
            mPaint.setColor(mPbTxtColor);
            int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
            canvas.drawText(mText, mProgressX, y, mPaint);
        } else {
            mTextWidth = 0;
            mPbTxtOffset = 0;
        }
        if (!mLostRight) {//绘制右侧
            drawRight(canvas);
        }

        canvas.restore();
    }

    /**
     * 对数值进行计算以及控制的flag设置
     */
    private void parseBeforeDraw() {
        mLostRight = false;//lostRight控制是否绘制右侧
        float radio = getProgress() * 1.f / getMax();//当前百分比率
        mProgressX = radio * mPBWidth;//进度条当前长度
        mEndX = mProgressX - mPbTxtOffset / 2;       //进度条当前长度-文字间隔的左半
        mText = getProgress() + "%";
        if (mProgressX + mTextWidth > mPBWidth) {
            mProgressX = mPBWidth - mTextWidth;
            mLostRight = true;
        }
        //文字宽度
        mTextWidth = (int) mPaint.measureText(mText);
    }

    /**
     * 绘制左侧：(进度条)
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        mPath.reset();
        mRectF.set(0, mPbOnHeight / 2, mEndX, -mPbOnHeight / 2);
        mPath.addRoundRect(mRectF, mFloat8Left, Path.Direction.CW);//顺时针画
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPbOnColor);
        canvas.drawPath(mPath, mPaint);//使用path绘制一端是圆头的线
    }

    /**
     * 当结束是执行：
     */
    private void whenOver() {
        mPbTxtGone = true;
        mFloat8Left = new float[]{//只有进度达到100时让进度圆角是四个
                mPbOnHeight / 2, mPbOnHeight / 2,//左上圆角x,y
                mPbOnHeight / 2, mPbOnHeight / 2,//右上圆角x,y
                mPbOnHeight / 2, mPbOnHeight / 2,//右下圆角x,y
                mPbOnHeight / 2, mPbOnHeight / 2//左下圆角x,y
        };
    }

    /**
     * 绘制左侧：(背景)
     *
     * @param canvas
     */
    private void drawRight(Canvas canvas) {
        float start = mProgressX + mPbTxtOffset / 2 + mTextWidth;
        mPaint.setColor(mPbBgColor);
        mPaint.setStrokeWidth(mPbBgHeight);

        mPath.reset();
        mRectF.set(start, mPbBgHeight / 2, mPBWidth, -mPbBgHeight / 2);
        mPath.addRoundRect(mRectF, mFloat8Right, Path.Direction.CW);//顺时针画
        canvas.drawPath(mPath, mPaint);//使用path绘制一端是圆头的线
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            //控件尺寸已经确定：如：
            // android:layout_height="40dp"或"match_parent"
            result = size;
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom() + Math.max(
                    Math.max(mPbBgHeight, mPbOnHeight), Math.abs(textHeight));

            if (mode == MeasureSpec.AT_MOST) {//最多不超过
                result = Math.min(result, size);
            }
        }
        return result;
    }


    public void setTxtGone(boolean drawText) {
        mPbTxtGone = drawText;
    }

    public boolean isTxtGone() {
        return mPbTxtGone;
    }

    protected int sp(int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    protected int dp(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
