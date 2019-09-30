package com.cchao.insomnia.ui.post;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.PostAddActivityBinding;
import com.cchao.insomnia.databinding.PostAddImageItemBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.global.GLobalInfo;
import com.cchao.insomnia.model.javabean.post.UploadImageBean;
import com.cchao.insomnia.util.ImageHelper;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.cchao.simplelib.util.StringHelper;
import com.google.android.flexbox.FlexboxLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑 发说说页
 *
 * @author : cchao
 * @version 2019-04-13
 */
public class AddPostActivity extends BaseTitleBarActivity<PostAddActivityBinding> implements View.OnClickListener {

    private static final int MAX_UPLOAD_IMAGE = 5;

    @Override
    protected int getLayout() {
        return R.layout.post_add_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(R.string.send_a_post);
        addTitleMenuItem(R.drawable.action_send, v -> onSendPost());
        mDataBind.setClicker(this);
    }

    private void onSendPost() {
        if (TextUtils.isEmpty(mDataBind.edit.getText().toString())) {
            showText(getString(R.string.please_input_content));
            return;
        }
        // 加入 图片的相对路径
        List<String> postImageList = new ArrayList<>();
        for (int i = 0; i < mDataBind.pictureGroup.getChildCount() - 1; i++) {
            View child = mDataBind.pictureGroup.getChildAt(i);
            if (child.getTag(R.id.bean_tag) != null) {
                UploadImageBean bean = (UploadImageBean) child.getTag(R.id.bean_tag);
                if (StringHelper.isEmpty(bean.getRelativeUrl())) {
                    showText(getString(R.string.image_no_upload));
                    return;
                }
                postImageList.add(bean.getRelativeUrl());
            }
        }

        // 发送添加说说 请求
        showProgress();
        addSubscribe(RetrofitHelper.getApis().addPost(mDataBind.edit.getText().toString(), StringUtils.join(postImageList, ","))
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                hideProgress();
                showText(respBean.getMsg());
                if (respBean.isCodeSuc()) {
                    finish();
                }
            }, RxHelper.getErrorTextConsumer(this)));
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                showImageSelect();
                break;
        }
    }

    private void showImageSelect() {
        int maxCount = 6 - mDataBind.pictureGroup.getChildCount();
        ImageHelper.takeImage(mContext, maxCount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null || requestCode != Constants.RequestCode.TAKE_IMAGE) {
            return;
        }

        List<String> pathList = data.getStringArrayListExtra("result");
        String tvResult = "";
        FlexboxLayout groupParent = mDataBind.pictureGroup;
        // 遍历上传
        for (String path : pathList) {
            PostAddImageItemBinding binding = DataBindingUtil.inflate(mLayoutInflater
                , R.layout.post_add_image_item, groupParent, false);
            groupParent.addView(binding.getRoot(), 0);
            uploadImage(binding, path);
            tvResult += path + "\n";
        }
        if (groupParent.getChildCount() > MAX_UPLOAD_IMAGE) {
            groupParent.removeViews(MAX_UPLOAD_IMAGE, groupParent.getChildCount());
        }
        if (GLobalInfo.isDebug()) {
            showText(tvResult);
        }
    }

    /**
     * 上传图片
     * 通过 setTag 将网络请求结果的bean放入view，
     */
    public void uploadImage(PostAddImageItemBinding binding, String localUri) {
        UploadImageBean bean = new UploadImageBean();
        bean.setLocalUri(localUri);
        binding.getRoot().setTag(R.id.bean_tag, bean);

        ImageHelper.uploadImage(mDisposable, "user_post", bean, respBean -> {
            binding.progress.setVisibility(View.GONE);

            ImageLoader.loadImage(binding.image, localUri, R.drawable.place_holder);
        }, RxHelper.getErrorTextConsumer(this));
    }
}
