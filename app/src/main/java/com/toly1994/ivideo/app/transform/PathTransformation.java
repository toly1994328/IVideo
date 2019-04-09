package com.toly1994.ivideo.app.transform;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/2/11/011:17:00<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.squareup.picasso.Transformation;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/8/30 0030:21:44<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：星星裁剪类
 */
public abstract class PathTransformation implements Transformation {
    private static final int STROKE_WIDTH = 2;

    private boolean mOutline;

    public PathTransformation( boolean outline) {
        mOutline = outline;
    }


    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint avatarPaint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        avatarPaint.setShader(shader);
        Paint outlinePaint = new Paint();
        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(STROKE_WIDTH);
        outlinePaint.setAntiAlias(true);

        Path path = formPath(source.getWidth(), source.getHeight());
        canvas.drawPath(path, avatarPaint);
        if (mOutline) {
            canvas.drawPath(path, outlinePaint);
        }
        squaredBitmap.recycle();
        return bitmap;
    }

    protected abstract Path formPath(int width, int height);


    @Override
    public String key() {
        return "StarTransformation";
    }
}