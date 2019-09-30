package com.cchao.insomnia.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author cchao
 * @version 9/9/18.
 */
public class AnimHelper {

    public static ObjectAnimator startRotate(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view
            , "rotation", 0f, 360f);
        rotation.setDuration(3600);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatMode(ValueAnimator.RESTART);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.start();
        return rotation;
    }

    public static void end(ObjectAnimator animator) {
        if (animator == null) {
            return;
        }
        animator.end();
    }

    public static void pause(ObjectAnimator animator) {
        if (animator == null) {
            return;
        }
        animator.pause();
    }

    public static void resumeOrStart(ObjectAnimator animator) {
        if (animator == null || animator.isRunning()) {
            return;
        }
        if (animator.isPaused()) {
            animator.resume();
        }
        animator.start();
    }
}