package com.cchao.insomnia.ui.fall;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.CommonRecyclerBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.insomnia.model.javabean.fall.FallImage;
import com.cchao.insomnia.ui.global.ImageShowActivity;
import com.cchao.insomnia.util.ImageHelper;
import com.cchao.insomnia.view.GridSpacingItemDecoration;
import com.cchao.insomnia.view.adapter.PageAdapter;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import io.reactivex.Observable;

/**
 * @author cchao
 * @version 8/12/18.
 */
public class FallImageActivity extends BaseTitleBarActivity<CommonRecyclerBinding> {

    RecyclerView mRecycler;
    PageAdapter<FallImage> mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.common_recycler;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(getString(R.string.image_list));
        initAdapter();
        onLoadData();
    }

    private void initAdapter() {
        mRecycler = mDataBind.recyclerView;
        mDataBind.refreshLayout.setEnabled(false);

        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2
            , StaggeredGridLayoutManager.VERTICAL));

        mRecycler.addItemDecoration(new GridSpacingItemDecoration(2
            , UiHelper.dp2px(10), true));

        mRecycler.setAdapter(mAdapter = new PageAdapter<FallImage>
            (R.layout.fall_image_item, mDisposable, this) {

            @Override
            protected Observable<RespListBean<FallImage>> getLoadObservable(int page) {
                return RetrofitHelper.getApis().getImageList(page);
            }

            int itemWidth = UiHelper.getScreenWidth() / 2 - UiHelper.dp2px(10);

            @Override
            protected void convert(DataBindViewHolder helper, FallImage item) {
                ImageView imageView = helper.getView(R.id.image);
//                helper.getBinding().setVariable(BR.item,item);
                helper.itemView.getLayoutParams().height = ImageHelper.getScaleHeight(itemWidth
                    , item.getWidth(), item.getHeight());

                ImageLoader.loadImage(imageView, item.getSrc());
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Router.turnTo(mContext, ImageShowActivity.class)
                    .putExtra(Constants.Extra.IMAGE_URL, mAdapter.getData().get(position).getSrc())
                    .putExtra(Constants.Extra.ID, mAdapter.getData().get(position).getId())
                    .start();
            }
        });

//        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    protected void onLoadData() {
        switchView(LOADING);
        mAdapter.onLoadData(1);

    }
}
