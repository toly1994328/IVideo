package com.toly1994.ivideo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.toly1994.ivideo.R;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/9 0009:11:49<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：圆形进度条
 */
public class TolyRoundProgressBar extends TolyProgressBar {

    private int mPbRadius = dp(30);//进度条半径
    private int mMaxPaintWidth;

    public TolyRoundProgressBar(Context context) {
        this(context, null);
    }

    public TolyRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TolyRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TolyRoundProgressBar);
        mPbRadius = (int) a.getDimension(R.styleable.TolyRoundProgressBar_z_pb_radius, mPbRadius);
        mPbOnHeight = (int) (mPbBgHeight * 1.8f);//让进度大一点
        a.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(mPbBgHeight, mPbOnHeight);
        int expect = mPbRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        int realWidth = Math.min(width, height);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPbRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;
        setMeasuredDimension(realWidth, realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String txt = getProgress() + "%";
        float txtWidth = mPaint.measureText(txt);
        float txtHeight = (mPaint.descent() + mPaint.ascent()) / 2;
        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        drawDot(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        //背景
        mPaint.setColor(mPbBgColor);
        mPaint.setStrokeWidth(mPbBgHeight);
        canvas.drawCircle(mPbRadius, mPbRadius, mPbRadius, mPaint);
        //进度条
        mPaint.setColor(mPbOnColor);
        mPaint.setStrokeWidth(mPbOnHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;//完成角度
        canvas.drawArc(
                0, 0, mPbRadius * 2, mPbRadius * 2,
                -90, sweepAngle, false, mPaint);
        //文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPbTxtColor);
        canvas.drawText(txt, mPbRadius - txtWidth / 2, mPbRadius - txtHeight / 2, mPaint);
        canvas.restore();
    }

    /**
     * 绘制一圈点
     *
     * @param canvas
     */
    private void drawDot(Canvas canvas) {
        canvas.save();

        int num = 40;
        canvas.translate(mPbRadius, mPbRadius);
        for (int i = 0; i < num; i++) {
            canvas.save();
            int deg = 360 / num * i;
            canvas.rotate(deg);
            mPaint.setStrokeWidth(mPbOnHeight);
            mPaint.setColor(mPbBgColor);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            if (i * (360 / num) < getProgress() * 1.f / getMax() * 360) {
                mPaint.setColor(mPbOnColor);
            }
            canvas.drawLine(0, mPbRadius * 3 / 4, 0, mPbRadius * 4 / 5, mPaint);
            canvas.restore();
        }
        canvas.restore();
    }
}
