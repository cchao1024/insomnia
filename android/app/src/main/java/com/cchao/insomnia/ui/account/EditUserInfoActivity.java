package com.cchao.insomnia.ui.account;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.UserInfoEditActivityBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.post.UploadImageBean;
import com.cchao.insomnia.util.ImageHelper;
import com.cchao.insomnia.view.adapter.EmailFilterAdapter;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.cchao.simplelib.util.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑用户信息
 *
 * @author cchao
 * @version 2019-05-08.
 */
public class EditUserInfoActivity extends BaseTitleBarActivity<UserInfoEditActivityBinding> implements View.OnClickListener {

    UploadImageBean mUploadImageBean = new UploadImageBean();

    @Override
    protected int getLayout() {
        return R.layout.user_info_edit_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(R.string.edit_user_info);
        mDataBind.setUser(UserManager.getUserBean());
        if (UserManager.isVisitor()) {
            setTitleText(R.string.bind_visitor_info);
            mDataBind.email.setText("");
        }

        addTitleMenuItem(R.drawable.action_down, v -> {
            onApply();
        });
        initView();
    }

    private void initView() {

        mDataBind.setClick(this);
        ImageLoader.loadImageCircle(mDataBind.avatar, UserManager.getUserBean().getAvatar(), R.drawable.default_portrait);

        mDataBind.check0.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mDataBind.check1.setChecked(false);
            }
        });
        mDataBind.check1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mDataBind.check0.setChecked(false);
            }
        });

        setEmailAdapter(mContext, mDataBind.email);
    }

    private void onApply() {
        if (StringHelper.isEmpty(mDataBind.email)) {
            showText(R.string.please_input_email);
            return;
        }
        if (StringHelper.isEmpty(mDataBind.pwd)) {
            showText(R.string.please_input_pwd);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("nickName", mDataBind.name.getText().toString());
        map.put("gender", String.valueOf(mDataBind.check0.isChecked() ? 0 : 1));
        map.put("age", mDataBind.age.getText().toString());
        map.put("email", mDataBind.email.getText().toString());
        map.put("password", mDataBind.pwd.getText().toString());
        if (StringHelper.isNotEmpty(mUploadImageBean.getRelativeUrl())) {
            map.put("avatar", mUploadImageBean.getRelativeUrl());
        }

        showProgress();
        addSubscribe(RetrofitHelper.getApis().editUserInfo(map)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                hideProgress();
                showText(respBean.getMsg());

                if (respBean.isCodeSuc()) {
                    UserManager.setUserBean(respBean.getData());
                    finish();
                }
            }, RxHelper.getHideProgressConsumer(this)));
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null || requestCode != Constants.RequestCode.TAKE_IMAGE) {
            return;
        }

        List<String> pathList = data.getStringArrayListExtra("result");
        uploadImage(pathList.get(0));
    }

    /**
     * 上传图片
     * 通过 setTag 将网络请求结果的bean放入view，
     */
    public void uploadImage(String localUri) {
        mUploadImageBean.setLocalUri(localUri);

        showProgress();
        ImageHelper.uploadImage(mDisposable, "user_avatar", mUploadImageBean, respBean -> {
            hideProgress();

            ImageLoader.loadImageCircle(mDataBind.avatar, localUri, R.drawable.default_portrait);
        }, RxHelper.getErrorTextConsumer(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                ImageHelper.takeImage(mContext, 1);
                break;
            case R.id.to_login_in:
                Router.turnTo(mContext, LogInActivity.class).start();
                break;
        }
    }

    public static void setEmailAdapter(Context context, AutoCompleteTextView view) {
        //设置自动完成
        ArrayList<String> mails = new ArrayList<>();
        mails.add("@qq.com");
        mails.add("@163.com");
        mails.add("@126.com");
        mails.add("@gmail.com");
        mails.add("@yahoo.com");
        EmailFilterAdapter adapter = new EmailFilterAdapter(context, mails);
        view.setAdapter(adapter);
    }
}
