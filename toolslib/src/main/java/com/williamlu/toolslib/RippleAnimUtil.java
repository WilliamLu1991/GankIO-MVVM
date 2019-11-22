package com.williamlu.toolslib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.annotation.RequiresApi;

/**
 * 创建者：WilliamLu
 * 创建时间：2017/5/24 17:24
 * 描述：水波纹动画工具类
 */

public class RippleAnimUtil {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void rippleAnimation(final View view) {
        final int centerX = view.getWidth() / 2;
        final int centerY = view.getHeight() / 2;
        final int maxRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view,
                centerX, centerY, maxRadius, 0);
        anim.setDuration(1000);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, maxRadius);
                anim.setDuration(1000);
                anim.start();
            }
        });
        anim.start();
    }
}
