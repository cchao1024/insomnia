package com.cchao.insomnia.ui.play;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.Apis;
import com.cchao.insomnia.databinding.Play478ActivityBinding;
import com.cchao.simplelib.Const;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.cchao.simplelib.ui.web.WebViewActivity;

import io.reactivex.functions.Consumer;

/**
 * 478
 *
 * @author cchao
 * @version 2019-05-13.
 */
public class Play478Activity extends BaseTitleBarActivity<Play478ActivityBinding> implements View.OnClickListener {
    boolean mIsPlaying;
    AnimatorSet mAnimatorSet;

    @Override
    protected int getLayout() {
        return R.layout.play_478_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(R.string.play_478);
        addTitleMenuItem(R.drawable.action_question, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.turnTo(mContext, WebViewActivity.class)
                    .putExtra(Const.Extra.Web_View_Tile, getString(R.string.desc))
                    .putExtra(Const.Extra.Web_View_Url, Apis.sleep478)
                    .start();
            }
        });
        mDataBind.setClick(this);
    }

    @Override
    protected void onLoadData() {

    }

    void startPlay() {
        mIsPlaying = true;
        mDataBind.actionButton.setText(R.string.stop);
        mAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
            R.animator.ball_anim);
        mAnimatorSet.setTarget(mDataBind.ball);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startTip();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                startTip();
            }
        });
        mAnimatorSet.start();
    }

    public void startTip() {
        mDataBind.stepTips.setText(R.string.breath_step_1);
        RxHelper.timerConsumer(4000, new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mDataBind.stepTips.setText(R.string.breath_step_2);
            }
        });
        RxHelper.timerConsumer(4000 + 7000, new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mDataBind.stepTips.setText(R.string.breath_step_3);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_button:
                if (mIsPlaying) {
                    finish();
                } else {
                    startPlay();
                }
                break;
        }
    }
}
