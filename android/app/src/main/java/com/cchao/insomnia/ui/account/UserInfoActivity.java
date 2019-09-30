package com.cchao.insomnia.ui.account;

import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.UserInfoActivityBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.ui.global.ImageShowActivity;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;

/**
 * 用户信息
 *
 * @author cchao
 * @version 2019-05-08.
 */
public class UserInfoActivity extends BaseTitleBarActivity<UserInfoActivityBinding> implements View.OnClickListener {
    @Override
    protected int getLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleBarVisible(false);
        mDataBind.setClick(this);
        mDataBind.setBean(UserManager.getUserBean());
        ImageLoader.loadImageCircle(mDataBind.avatar, UserManager.getUserBean().getAvatar(), R.drawable.default_portrait);
//        int genderDrawable=UserManager.getUserBean().getGender()==1?R.drawable.user_info_gender
//        mDataBind.name.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,,0);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_user_info:
                Router.turnTo(mContext, EditUserInfoActivity.class).start();
                break;
            case R.id.title_background:
                showText(R.string.developing);
                break;
            case R.id.avatar:
                Router.turnTo(mContext, ImageShowActivity.class)
                    .putExtra(Constants.Extra.IMAGE_URL, UserManager.getUserBean().getAvatar())
                    .start();
                break;
        }
    }
}
