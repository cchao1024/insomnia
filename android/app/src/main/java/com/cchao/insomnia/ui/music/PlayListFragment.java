package com.cchao.insomnia.ui.music;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cchao.insomnia.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.MusicPlayListFragmentBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.insomnia.view.adapter.DataBindQuickAdapter;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.UiHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * 播放列表
 *
 * @author cchao
 * @version 9/8/18.
 */
public class PlayListFragment extends DialogFragment implements View.OnClickListener {

    RecyclerView mRecycler;
    DataBindQuickAdapter<FallMusic> mAdapter;
    Activity mActivity;

    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
    }

    public PlayListFragment setActivity(Activity activity) {
        mActivity = activity;
        RxBus.get().toObservable(event -> {
            if (Constants.Event.Update_Play_Status != event.getCode()) {
                return;
            }
            switch (event.getContent()) {
                case MusicPlayer.State.Prepare:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        });

        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MusicPlayListFragmentBinding binding = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.music_play_list_fragment, null, false);
        mRecycler = binding.recyclerView;
        binding.setClick(this);

        // 不带style的构建的dialog宽度无法铺满屏幕
        //     Dialog dialog = new Dialog(mActivity);
        Dialog dialog = new Dialog(mActivity, R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);

        // 设置弹出框布局参数，宽度铺满全屏，底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        return dialog;
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration divider = new DividerItemDecoration(mActivity
            , DividerItemDecoration.VERTICAL);
        divider.setDrawable(UiHelper.getDrawable(R.drawable.music_list_divider));
        mRecycler.addItemDecoration(divider);

        mRecycler.setAdapter(mAdapter = new DataBindQuickAdapter<FallMusic>(R.layout.play_list_item, MusicPlayer.mPlayList) {
            @Override
            protected void convert(DataBindViewHolder helper, FallMusic item) {
                helper.getBinding().setVariable(BR.item, item);
                helper.getView(R.id.remove).setOnClickListener(v -> {
                    MusicPlayer.removeFromPlayList(item);
                    mAdapter.notifyDataSetChanged();
                });
                UiHelper.setVisibleElseGone(helper.getView(R.id.playing), MusicPlayer.isCurPlaying(item));
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MusicPlayer.playNow(mAdapter.getItem(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_all:
                MusicPlayer.clearAndStop();
                dismiss();
                break;
        }
    }
}
