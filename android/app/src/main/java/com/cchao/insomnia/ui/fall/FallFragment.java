package com.cchao.insomnia.ui.fall;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cchao.insomnia.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.FallFragmentBinding;
import com.cchao.insomnia.databinding.FallHeadBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.insomnia.model.javabean.fall.FallImage;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.insomnia.ui.global.ImageShowActivity;
import com.cchao.insomnia.util.ImageHelper;
import com.cchao.insomnia.view.Dialogs;
import com.cchao.insomnia.view.GridSpaceDividerDecoration;
import com.cchao.insomnia.view.GridSpacingItemDecoration;
import com.cchao.insomnia.view.adapter.DataBindQuickAdapter;
import com.cchao.insomnia.view.adapter.PageAdapter;
import com.cchao.simplelib.core.GlideApp;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.fragment.BaseStatefulFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import io.reactivex.Observable;

/**
 * @author cchao
 * @version 8/10/18.
 */
public class FallFragment extends BaseStatefulFragment<FallFragmentBinding> implements View.OnClickListener {

    RecyclerView mRvMusic;
    RecyclerView mRvImage;
    RecyclerView mRvNature;

    /**
     * adapter
     */
    DataBindQuickAdapter<FallMusic> mMusicAdapter;
    PageAdapter<FallImage> mImageAdapter;
    FallHeadBinding mHeadBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.fall_fragment;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initEvent();
        initImageAdapter();
        initMusicAdapter();
        onLoadData();
    }

    @Override
    protected void onLoadData() {
        switchView(LOADING);
        addSubscribe(RetrofitHelper.getApis().getIndexData()
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                switchView(CONTENT);
                mImageAdapter.setNewData(respBean.getData().getFallImages());
                mMusicAdapter.setNewData(respBean.getData().getMusic());
            }, throwable -> {
                switchView(NET_ERROR);
                Logs.logException(throwable);
            }));
    }

    private void initView() {
        mHeadBinding = DataBindingUtil.inflate(mLayoutInflater
            , R.layout.fall_head, null, false);
        mRvMusic = mHeadBinding.rvMusic;
        mRvNature = mHeadBinding.rvNature;

        mRvImage = mDataBind.rvImage;

        mHeadBinding.setClicker(this);
        mDataBind.setClicker(this);
    }

    private void initEvent() {
        addSubscribe(RxBus.get().toObservable(event -> {
            switch (event.getCode()) {
            }
        }));
    }

    /**
     * 设置音频列表适配器
     */
    private void initMusicAdapter() {
        mRvMusic.setHasFixedSize(true);
        mRvMusic.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false));
        mRvMusic.addItemDecoration(new GridSpaceDividerDecoration(UiHelper.dp2px(10), 2));
        mRvMusic.setAdapter(mMusicAdapter = new DataBindQuickAdapter<FallMusic>(R.layout.fall_music_item) {
            @Override
            protected void convert(DataBindViewHolder helper, FallMusic item) {
                helper.getBinding().setVariable(BR.item, item);
                helper.getView(R.id.more).setOnClickListener(click -> {
                    Dialogs.showMusicItemMenu(mLayoutInflater, item, FallFragment.this);
                });

                helper.getConvertView().setOnLongClickListener(v -> {
                    Dialogs.showMusicItemMenu(mLayoutInflater, item, FallFragment.this);
                    return true;
                });
            }
        });
        mMusicAdapter.setOnItemClickListener((adapter, view, position) -> {
            FallMusic item = mMusicAdapter.getItem(position);
            MusicPlayer.playOrPause(item);
        });
    }

    /**
     * 设置图片列表适配器
     */
    private void initImageAdapter() {
        mRvImage.addItemDecoration(new GridSpacingItemDecoration(2, UiHelper.dp2px(10), true) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildViewHolder(view).getItemViewType() == BaseQuickAdapter.HEADER_VIEW) {
                    outRect.setEmpty();
                    return;
                }
                super.getItemOffsets(outRect, view, parent, state);
            }
        });

        mRvImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvImage.setAdapter(mImageAdapter = new PageAdapter<FallImage>(R.layout.fall_image_item
            , mDisposable, this) {

            int itemWidth = UiHelper.getScreenWidth() / 2;

            @Override
            protected Observable<RespListBean<FallImage>> getLoadObservable(int page) {
                return RetrofitHelper.getApis().getImageList(page);
            }

            @Override
            protected void convert(DataBindViewHolder helper, FallImage item) {
                helper.itemView.getLayoutParams().width = itemWidth;
                helper.itemView.getLayoutParams().height = ImageHelper
                    .getScaleHeight(itemWidth, item.getWidth(), item.getHeight());

//                ImageLoader.loadImage(helper.getView(R.id.image), item.getSrc());

                GlideApp.with(helper.getView(R.id.image).getContext())
                    .asGif()
                    .load(item.getSrc())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.place_holder)
                    .fitCenter()
                    .into((ImageView) helper.getView(R.id.image));
            }
        });
        mImageAdapter.setOnItemClickListener((adapter, view, position) -> {
            Router.turnTo(mContext, ImageShowActivity.class)
                .putExtra(Constants.Extra.IMAGE_URL, mImageAdapter.getData().get(position).getSrc())
                .putExtra(Constants.Extra.ID, mImageAdapter.getData().get(position).getId())
                .start();
        });
        mImageAdapter.addHeaderView(mHeadBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_more:
                Router.turnTo(mContext, FallMusicListActivity.class)
                    .start();
                break;
            case R.id.image_more:
                Router.turnTo(mContext, FallImageActivity.class)
                    .start();
                break;
            case R.id.music_disk:
//                MusicPlayer.clickDisk();
//                Router.turnTo(mContext, MusicPlayerActivity.class).start();
                break;
            default:
                break;
        }
    }
}
