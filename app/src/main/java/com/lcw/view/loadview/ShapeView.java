package com.lcw.view.loadview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义图形（三角形，圆形，正方形）
 * Create by: chenwei.li
 * Date: 2017/10/26
 * time: 14:11
 * Email: lichenwei.me@foxmail.com
 */

public class ShapeView extends View {

    private Paint mPaint;
    private ShapeEnum mCurrentShape;

    /**
     * 形状枚举
     */
    public enum ShapeEnum {
        SHAPE_RECT, SHAPE_CIRCLE, SHAPE_TRIANGLE
    }

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 进行一些初始化操作
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mCurrentShape = ShapeEnum.SHAPE_RECT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //根据当前图形形状绘制图形
        switch (mCurrentShape) {
            case SHAPE_RECT:
                mPaint.setColor(Color.parseColor("#aae84e40"));
                canvas.drawRect(0, 0, width, height, mPaint);
                break;
            case SHAPE_CIRCLE:
                mPaint.setColor(Color.parseColor("#aa738ffe"));
                canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);
                break;
            case SHAPE_TRIANGLE:
                mPaint.setColor(Color.parseColor("#aa72d572"));
                Path path = new Path();
                path.moveTo(0, (float) (Math.sqrt(3) / 2 * width));
                path.lineTo(width, (float) (Math.sqrt(3) / 2 * width));
                path.lineTo(width / 2, 0);
                path.close();
                canvas.drawPath(path, mPaint);
                break;
        }
    }

    public void changeShapeView() {
        //根据当前图形形状绘制下一个图形
        switch (mCurrentShape) {
            case SHAPE_RECT:
                mCurrentShape = ShapeEnum.SHAPE_TRIANGLE;
                break;
            case SHAPE_CIRCLE:
                mCurrentShape = ShapeEnum.SHAPE_RECT;
                break;
            case SHAPE_TRIANGLE:
                mCurrentShape = ShapeEnum.SHAPE_CIRCLE;
                break;
        }
        invalidate();

        startRotateAnimation();
    }

    /**
     * 开启旋转动画
     */
    public void startRotateAnimation() {
        //根据当前图形形状设置动画
        switch (mCurrentShape) {
            case SHAPE_RECT:
                //旋转动画
                ObjectAnimator rectAnimation = ObjectAnimator.ofFloat(this, "rotation", 0, 180);
                rectAnimation.setDuration(500);
                rectAnimation.start();
                break;
            case SHAPE_CIRCLE:
            case SHAPE_TRIANGLE:
                //旋转动画
                ObjectAnimator triangleAnimation = ObjectAnimator.ofFloat(this, "rotation", 0, -120);
                triangleAnimation.setDuration(500);
                triangleAnimation.start();
                break;
        }
    }


}
