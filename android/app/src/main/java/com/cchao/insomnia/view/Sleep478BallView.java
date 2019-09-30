package com.cchao.insomnia.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cchao.simplelib.core.UiHelper;

/**
 * @author cchao
 * @version 2019-05-13.
 */
public class Sleep478BallView extends View {
    Paint mPaint;
    int lineWidth = 10;
    int ratio = 50;
    int mColor = Color.BLUE;
    float rotate;

    public Sleep478BallView(Context context) {
        this(context, null);
    }

    public Sleep478BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Sleep478BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 由于动画中有更改颜色，所以这里每次onDraw都要重新设置下画笔颜色
        mPaint.setColor(mColor);
        // 画圆形，ratio为半径
        canvas.drawCircle(getWidth() / 2 - ratio / 2, getHeight() / 2 - ratio / 2, ratio, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void start() {
        blowUpAnim();
    }

    public void stop() {
        animate().cancel();
    }

    @NonNull
    private void blowUpAnim() {
        animate().scaleXBy(UiHelper.dp2px(100))
            .setDuration(4 * 1000)
            .withEndAction(new Runnable() {
                @Override
                public void run() {
                    blowDownAnim();
                }
            })
            .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            })
            .start();
    }

    @NonNull
    private void blowDownAnim() {
        animate().scaleXBy(-UiHelper.dp2px(100))
            .setStartDelay(7 * 1000)
            .setDuration(8 * 1000)
            .withEndAction(new Runnable() {
                @Override
                public void run() {
                    start();
                }
            })
            .start();
    }
}
