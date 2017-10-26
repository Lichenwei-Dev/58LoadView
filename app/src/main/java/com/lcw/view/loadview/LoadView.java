package com.lcw.view.loadview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * 自定义加载布局
 * Create by: chenwei.li
 * Date: 2017/10/26
 * time: 10:02
 * Email: lichenwei.me@foxmail.com
 */

public class LoadView extends LinearLayout {

    //加载图形
    private ShapeView mShapeView;
    //图形阴影
    private View mShadowView;


    public LoadView(Context context) {
        this(context,null);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化布局操作
     */
    private void init(Context context) {
        inflate(context,R.layout.loadview,this);
        mShapeView= (ShapeView) findViewById(R.id.iv_loadview_shape);
        mShadowView= findViewById(R.id.iv_loadview_shadow);
        startAnimationDown(context);
    }

    /**
     * 开始向下动画以及阴影缩小
     */
    private void startAnimationDown(final Context context){
        //开始向下动画
        ObjectAnimator animatorDown=ObjectAnimator.ofFloat(mShapeView,"translationY",0,dip2px(context,50));
        //在动画开始的地方速率改变比较慢，然后开始加速
        animatorDown.setInterpolator(new AccelerateDecelerateInterpolator());

        //阴影缩小动画
        ObjectAnimator animatorScale=ObjectAnimator.ofFloat(mShadowView,"scaleX",1.0f,0.3f);

        //复合动画
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(animatorDown,animatorScale);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShapeView.changeShapeView();
                startAnimationUp(context);
            }
        });
    }

    /**
     * 开始向上动画以及阴影放大
     */
    private void startAnimationUp(final Context context){
        //开始向上动画
        ObjectAnimator animatorDown=ObjectAnimator.ofFloat(mShapeView,"translationY",dip2px(context,50),0);
        //在动画开始的地方快然后慢
        animatorDown.setInterpolator(new DecelerateInterpolator());

        //阴影缩小动画
        ObjectAnimator animatorScale=ObjectAnimator.ofFloat(mShadowView,"scaleX",0.3f,1f);


        //复合动画
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(animatorDown,animatorScale);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startAnimationDown(context);
            }
        });

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
