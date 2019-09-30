package com.cchao.insomnia.ui.fall;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cchao.insomnia.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.MusicListBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.insomnia.view.Dialogs;
import com.cchao.insomnia.view.adapter.PageAdapter;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;

import io.reactivex.Observable;

/**
 * @author cchao
 * @version 8/12/18.
 */
public class FallMusicListActivity extends BaseTitleBarActivity<MusicListBinding> {

    RecyclerView mRecycler;
    PageAdapter<FallMusic> mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.music_list;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(getString(R.string.music_list));
        initAdapter();
        initEvent();
        onLoadData();
    }

    private void initEvent() {
        addSubscribe(RxBus.get().toObservable(event -> {
            if (Constants.Event.Update_Play_Status != event.getCode()) {
                return;
            }
            switch (event.getContent()) {
                case MusicPlayer.State.Prepare:
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        long id = mAdapter.getData().get(i).getId();
                        if (id == MusicPlayer.getCurPlayingId() || id == MusicPlayer.getPrePlayingId()) {
                            mAdapter.notifyItemChanged(i);
                        }
                    }
                    break;
            }
        }));
    }

    private void initAdapter() {
        mRecycler = mDataBind.recyclerView;
        mDataBind.refreshLayout.setEnabled(false);

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(UiHelper.getDrawable(R.drawable.music_list_divider));
        mRecycler.addItemDecoration(divider);

        mRecycler.setAdapter(mAdapter = new PageAdapter<FallMusic>
            (R.layout.music_item, mDisposable, this) {

            @Override
            protected Observable<RespListBean<FallMusic>> getLoadObservable(int page) {
                return RetrofitHelper.getApis().getMusicList(page);
            }

            @Override
            protected void convert(DataBindViewHolder helper, FallMusic item) {
                helper.getBinding().setVariable(BR.item, item);
                helper.setText(R.id.order_num, helper.getLayoutPosition() + 1 + "");

                UiHelper.setVisibleElseGone(helper.getView(R.id.playing), MusicPlayer.isCurPlaying(item));
                // 弹出 menu对话框
                helper.getView(R.id.more).setOnClickListener(v -> {
                    Dialogs.showMusicItemMenu(mLayoutInflater, item, FallMusicListActivity.this);
                });
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            FallMusic item = mAdapter.getItem(position);
            MusicPlayer.playNow(item);
        });
//        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    protected void onLoadData() {
        switchView(LOADING);
        mAdapter.onLoadData(1);

    }
}
