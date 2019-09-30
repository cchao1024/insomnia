package com.cchao.insomnia.ui.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cchao.insomnia.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.PostDetailActivityBinding;
import com.cchao.insomnia.databinding.PostDetailCommentBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.post.CommentVO;
import com.cchao.insomnia.model.javabean.post.PostVO;
import com.cchao.insomnia.model.javabean.post.ReplyVO;
import com.cchao.insomnia.ui.global.ImageShowActivity;
import com.cchao.insomnia.ui.post.convert.CommentConvert;
import com.cchao.insomnia.view.adapter.DataBindMultiQuickAdapter;
import com.cchao.insomnia.view.wish.WishView;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.cchao.simplelib.util.CallBacks;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帖子详情页， recycler view， add详情header，
 * recyclerView 的itemBean 是经过转化的，使用统一的类型CommentConvert
 *
 * @author : cchao
 * @version 2019-04-05
 */
public class PostDetailActivity extends BaseTitleBarActivity<PostDetailActivityBinding> implements View.OnClickListener {
    long mId;
    DataBindMultiQuickAdapter<CommentConvert> mAdapter;
    PostVO mPostVO;

    @Override
    protected int getLayout() {
        return R.layout.post_detail_activity;
    }

    @Override
    protected void initEventAndData() {
        mId = getIntent().getLongExtra(Constants.Extra.ID, 0);
        setTitleText(R.string.post_detail);
        mDataBind.setClicker(this);
        initAdapter();
        switchView(LOADING);
        onLoadData();
    }

    private void initAdapter() {
        mAdapter = new DataBindMultiQuickAdapter<CommentConvert>(null) {
            @Override
            public Map<Integer, Integer> getTypeLayoutMap() {
                Map<Integer, Integer> map = new HashMap<>(4);
                map.put(CommentConvert.TYPE_COMMENT, R.layout.post_comment_item);
                map.put(CommentConvert.TYPE_REPLY, R.layout.post_reply_item);
                map.put(CommentConvert.TYPE_REPLY_Back, R.layout.post_reply_item);
                return map;
            }

            @Override
            protected void convert(DataBindViewHolder helper, CommentConvert item) {
                UserManager.processIsWish(item);
                helper.getBinding().setVariable(BR.item, item);
                switch (helper.getItemViewType()) {
                    case CommentConvert.TYPE_COMMENT:
                        break;
                    case CommentConvert.TYPE_REPLY:
                        UiHelper.setVisibleElseGone(helper.getView(R.id.reply_label), false);
                        UiHelper.setVisibleElseGone(helper.getView(R.id.to_user_name), false);
                        break;
                    case CommentConvert.TYPE_REPLY_Back:
                        UiHelper.setVisibleElseGone(helper.getView(R.id.reply_label), true);
                        UiHelper.setVisibleElseGone(helper.getView(R.id.to_user_name), true);
                        helper.setText(R.id.to_user_name, item.getToUserName());
                        break;
                }

                helper.getView(R.id.reply).setOnClickListener(view -> {
                    showCommentDialog("reply", item.getCommentId(), item.getReplyId());
                });
                WishView wishView = helper.getView(R.id.like);
                wishView.setWishCallBack(new CallBacks.Bool() {
                    @Override
                    public void onCallBack(boolean bool) {
                        item.addLike(new Runnable() {
                            @Override
                            public void run() {
                                wishView.updateToggle(item.getMSourceBean().isLiked(), item.getMSourceBean().getLikeCount());
                            }
                        });
                    }
                });
            }
        };
        mDataBind.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBind.recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.post_empty, mDataBind.recyclerView);
    }

    @Override
    protected void onLoadData() {
        addSubscribe(RetrofitHelper.getApis().getPostDetail(mId)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                hideProgress();
                if (respBean.isCodeFail()) {
                    showText(respBean.getMsg());
                    switchView(NET_ERROR);
                    return;
                }
                switchView(CONTENT);
                updateDetail(respBean.getData());
                convertData(respBean.getData().getList());
            }, RxHelper.getSwitchErrorConsumer(this)));
    }

    private void convertData(List<CommentVO> list) {
        List<CommentConvert> result = new ArrayList<>();

        // 遍历处理数据
        for (CommentVO commentVO : list) {
            result.add(CommentConvert.fromCommentVo(commentVO));
            // 遍历reply
            for (ReplyVO replyVO : commentVO.getList()) {
                result.add(CommentConvert.fromReplyVO(replyVO));
            }
        }
        mAdapter.setNewData(result);
        mAdapter.loadMoreEnd();
    }

    /**
     * 更新detail
     */
    private void updateDetail(PostVO data) {
        UserManager.processIsWish(data);
        mPostVO = data;
        mDataBind.setItem(data);
        updateImageBox(mDataBind.flexBox, data.getImageList());

        // 点赞
        mDataBind.like.updateToggle(mPostVO.isLiked(), mPostVO.getLikeCount());
        mDataBind.like.setWishCallBack(new CallBacks.Bool() {
            @Override
            public void onCallBack(boolean bool) {
                UserManager.addLike("post", mPostVO.getId(), bool1 -> {
                    if (bool1) {
                        mPostVO.setLiked(true);
                        mPostVO.setLikeCount(mPostVO.getLikeCount() + 1);
                    }
                    mDataBind.like.updateToggle(mPostVO.isLiked(), mPostVO.getLikeCount());
                    RxBus.get().postEvent(Constants.Event.update_post_like_count, mPostVO);
                });
            }
        });
    }

    /**
     * 每个Item 都有5个 imageview，如果有没有imagePath 就set gone，
     */
    void updateImageBox(FlexboxLayout box, List<String> imageList) {
        for (int i = 0; i < Constants.Config.MAX_POST_IMAGE; i++) {
            ImageView view = (ImageView) box.getChildAt(i);
            // 有才显示
            UiHelper.setVisibleElseGone(view, i < imageList.size());
            if (i < imageList.size()) {
                ImageLoader.loadImage(view, imageList.get(i));
            }
            // 点击跳转大图
            int finalI = i;
            view.setOnClickListener(v -> Router.turnTo(mContext, ImageShowActivity.class)
                .putExtra(Constants.Extra.IMAGE_URL, imageList.get(finalI))
                .start());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_action:
                showCommentDialog("comment", mPostVO.getId(), 0);
                break;
        }
    }

    /**
     * 显示评论对话框，用户输入，点发送
     *
     * @param type    为 comment 时 toId 表示 postId，
     *                为 reply 是 toId 表示 commentId
     * @param replyId 不为空 为 回复用户
     */
    private void showCommentDialog(String type, long toId, long replyId) {
        PostDetailCommentBinding binding = DataBindingUtil.inflate(mLayoutInflater
            , R.layout.post_detail_comment, null, false);

        // 弹出对话框
        Dialog dialog = new AlertDialog.Builder(mContext)
            .setTitle(getString(R.string.what_you_think))
            .setView(binding.getRoot())
            .show();

        binding.send.setOnClickListener(click -> {
            if (TextUtils.isEmpty(binding.edit.getText().toString())) {
                showText(R.string.can_not_empty_content);
                return;
            }
            onSendComment(binding, type, toId, replyId, () -> {
                dialog.dismiss();
                showProgress();
                onLoadData();
            });
        });
    }

    /**
     * 发送评论，
     * 如果toUserId == PostUserId 则为评论，否则为回复
     */
    void onSendComment(PostDetailCommentBinding binding, String type, long id, long replyId, Runnable callback) {
        UiHelper.setVisibleElseGone(binding.send, false);
        UiHelper.setVisibleElseGone(binding.progress, true);

        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("toId", String.valueOf(id));
        map.put("replyId", String.valueOf(replyId));
        map.put("content", binding.edit.getText().toString());
        map.put("images", "");

        addSubscribe(RetrofitHelper.getApis().addCommentOrReply(type, map)
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
}
