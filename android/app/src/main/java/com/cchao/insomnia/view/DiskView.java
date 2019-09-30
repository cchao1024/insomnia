package com.cchao.insomnia.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.MusicDiscBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.ui.music.PlayListFragment;
import com.cchao.insomnia.util.AnimHelper;
import com.cchao.simplelib.core.CollectionHelper;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.UiHelper;

/**
 * 底部的 music 盘
 *
 * @author cchao
 * @version 2019-07-05.
 */
public class DiskView extends RelativeLayout implements View.OnClickListener {
    Context mContext;
    PlayListFragment mPlayListFragment;
    MusicDiscBinding mBinding;
    ObjectAnimator mAnimator;

    public DiskView(Context context) {
        this(context, null, 0);
    }

    public DiskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.music_disc, this, false);
        addView(mBinding.getRoot());
        UiHelper.setVisibleElseGone(mBinding.getRoot(), false);
        mBinding.setClick(this);
        RxBus.get().toObservable(event -> {
            if (event.getCode() != Constants.Event.Update_Play_Status) {
                return;
            }
            switch (event.getContent()) {
                case MusicPlayer.State.Init:
                    UiHelper.setVisibleElseGone(mBinding.controllerField, false);
                    mBinding.controllerField.setTag(R.id.boolean_tag, false);
                    setVisibility(GONE);
                    AnimHelper.end(mAnimator);
                    break;
                case MusicPlayer.State.Pause:
                case MusicPlayer.State.Prepare:
                    mBinding.playPause.setImageResource(R.drawable.music_play);
                    setVisibility(VISIBLE);
                    AnimHelper.pause(mAnimator);
                    break;
                case MusicPlayer.State.Playing:
                    mBinding.playPause.setImageResource(R.drawable.music_pause);
                    if (mAnimator == null) {
                        AnimHelper.startRotate(mBinding.disk);
                    } else {
                        AnimHelper.resumeOrStart(mAnimator);
                    }
                    break;
            }

            UiHelper.setVisibleElseGone(mBinding.getRoot(), CollectionHelper.isNotEmpty(MusicPlayer.mPlayList));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.disk:
                if (CollectionHelper.isEmpty(MusicPlayer.mPlayList)) {
                    return;
                }

                boolean tag = true;
                if (mBinding.controllerField.getTag(R.id.boolean_tag) != null) {
                    tag = !(boolean) mBinding.controllerField.getTag(R.id.boolean_tag);
                }

                UiHelper.setVisibleElseGone(mBinding.controllerField, tag);
                mBinding.controllerField.setTag(R.id.boolean_tag, tag);
                break;
            case R.id.play_pre:
                MusicPlayer.playPre();
                break;
            case R.id.play_next:
                MusicPlayer.playNext();
                break;
            case R.id.play_pause:
                MusicPlayer.playOrPause();
                break;
            case R.id.play_list:
                if (mPlayListFragment == null) {
                    mPlayListFragment = new PlayListFragment().setActivity(((AppCompatActivity) (mContext)));
                }
                mPlayListFragment.show(((AppCompatActivity) (mContext)).getSupportFragmentManager(), "PlayListFragment");
                mBinding.disk.performClick();
                break;
        }
    }
}
