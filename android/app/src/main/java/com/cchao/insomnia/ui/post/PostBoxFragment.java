package com.cchao.insomnia.ui.post;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.PostBoxBinding;
import com.cchao.insomnia.databinding.PostDetailCommentBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.insomnia.model.javabean.post.PostListVO;
import com.cchao.insomnia.model.javabean.post.PostVO;
import com.cchao.insomnia.util.TimeHelper;
import com.cchao.insomnia.view.adapter.PageAdapter;
import com.cchao.insomnia.view.wish.WishView;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.model.event.CommonEvent;
import com.cchao.simplelib.ui.fragment.BaseStatefulFragment;
import com.cchao.simplelib.util.CallBacks;
import com.cchao.simplelib.util.StringHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import io.reactivex.Observable;

/**
 * 聊天室
 *
 * @author : cchao
 * @version 2019-04-05
 */
public class PostBoxFragment extends BaseStatefulFragment<PostBoxBinding> implements View.OnClickListener {

    PageAdapter<PostListVO> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.post_box;
    }

    @Override
    protected void initEventAndData() {
        mDataBind.setClicker(this);
        mDataBind.refreshLayout.setOnRefreshListener(() -> onLoadData());
        initAdapter();
        onLoadData();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (event.getCode()) {
            case Constants.Event.update_post_comment_count:
            case Constants.Event.update_post_like_count:
                PostVO eventPost = event.getBean();
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    if (eventPost.getId() == mAdapter.getItem(i).getId()) {
                        PostListVO postListVO = mAdapter.getItem(i);
                        postListVO.setLikeCount(eventPost.getLikeCount());
                        postListVO.setReviewCount(postListVO.getReviewCount() + 1);
                        mAdapter.setData(i, postListVO);
                    }
                }
                break;
        }
    }

    private void initAdapter() {
        mDataBind.recyclerView.setAdapter(mAdapter = new PageAdapter<PostListVO>
            (R.layout.post_box_item, mDisposable, this) {

            @Override
            public void solvePage(int page, RespListBean<PostListVO> respBean) {
                super.solvePage(page, respBean);
                mDataBind.refreshLayout.setRefreshing(false);
            }

            @Override
            protected Observable<RespListBean<PostListVO>> getLoadObservable(int page) {
                return RetrofitHelper.getApis().getPostBoxList(page);
            }

            @Override
            protected void convert(DataBindViewHolder helper, PostListVO item) {
                UserManager.processIsWish(item);
                helper.getBinding().setVariable(BR.item, item);
                helper.setText(R.id.date, TimeHelper.getStandardDate(item.getUpdateTime()));
                updateImageBox(helper.getView(R.id.flex_box), item);
                helper.setOnClickListener(R.id.comment_button, v -> showCommentDialog(item.getId()));
                ((WishView) helper.getView(R.id.like)).setWishCallBack(new CallBacks.Bool() {
                    @Override
                    public void onCallBack(boolean bool) {
                        UserManager.addLike("post", item.getId()
                            , bool1 -> {
                                if (bool1) {
                                    item.setLiked(true);
                                    item.setLikeCount(item.getLikeCount() + 1);
                                }
                                ((WishView) helper.getView(R.id.like)).updateToggle(item.isLiked(), item.getLikeCount());
                            });
                    }
                });
            }

            /**
             * 每个Item 都有5个 imageview，如果有没有imagePath 就set gone，
             * @param item bean
             */
            private void updateImageBox(FlexboxLayout box, @NonNull PostListVO item) {
                List<String> imageList = item.getImageList();
                for (int i = 0; i < Constants.Config.MAX_POST_IMAGE; i++) {
                    ImageView view = (ImageView) box.getChildAt(i);
                    UiHelper.setVisibleElseGone(view, i < imageList.size());

                    // 有才显示
                    if (i < imageList.size()) {
                        ImageLoader.loadImage(view, imageList.get(i));
                    }
                }
            }
        });

        mDataBind.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Router.turnTo(mContext, PostDetailActivity.class)
                    .putExtra(Constants.Extra.ID, mAdapter.getData().get(position).getId())
                    .start();
            }
        });
    }

    /**
     * 显示评论对话框，用户输入，点发送
     *
     * @param id 评论id
     */
    void showCommentDialog(long id) {
        PostDetailCommentBinding binding = DataBindingUtil.inflate(mLayoutInflater
            , R.layout.post_detail_comment, null, false);

        // 弹出对话框
        Dialog dialog = new AlertDialog.Builder(mContext)
            .setTitle(R.string.what_you_think)
            .setView(binding.getRoot())
            .show();

        binding.send.setOnClickListener(click -> {
            if (StringHelper.isEmpty(binding.edit)) {
                showText(getString(R.string.can_not_empty_content));
            }
            onSendComment(binding, id, () -> {
                dialog.dismiss();
            });
        });
    }

    /**
     * 发送评论，
     */
    void onSendComment(PostDetailCommentBinding binding, long id, Runnable callback) {
        UiHelper.setVisibleElseGone(binding.send, false);
        UiHelper.setVisibleElseGone(binding.progress, true);

        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("commentId", String.valueOf(id));
        map.put("content", binding.edit.getText().toString());
        map.put("images", "");

        addSubscribe(RetrofitHelper.getApis().addCommentOrReply("comment", map)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                showText(respBean.getMsg());
                if (respBean.isCodeSuc()) {
                    callback.run();
                }

                UiHelper.setVisibleElseGone(binding.send, true);
                UiHelper.setVisibleElseGone(binding.progress, false);
            }, throwable -> {
                Logs.logException(throwable);
                showError();

                UiHelper.setVisibleElseGone(binding.send, true);
                UiHelper.setVisibleElseGone(binding.progress, false);
            }));
    }

    @Override
    protected void onLoadData() {
        switchView(LOADING);
        mAdapter.onLoadData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_action:
                Router.turnTo(mContext, AddPostActivity.class).start();
                break;
        }
    }
}
