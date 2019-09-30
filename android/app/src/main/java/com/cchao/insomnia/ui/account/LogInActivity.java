package com.cchao.insomnia.ui.account;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.LoginActivityBinding;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.cchao.simplelib.util.StringHelper;

/**
 * 登录页
 *
 * @author cchao
 * @version 2/18/19.
 */
public class LogInActivity extends BaseTitleBarActivity<LoginActivityBinding> implements View.OnClickListener {

    AutoCompleteTextView mEmailEdit;
    EditText mPasswordEdit;

    @Override
    protected int getLayout() {
        return R.layout.login_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleBarVisible(false);
        mDataBind.setClick(this);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgot_your_password:
/*                Router.turnTo(mContext, ForgetPwdActivity.class)
                    .putExtra(Constants.Extra.E_MAIL, mEmailEdit.getText().toString())
                    .start();*/
                showText(R.string.developing);
                break;
            case R.id.login:
                String email = mEmailEdit.getText().toString();
                String password = mPasswordEdit.getText().toString();

                //为空
                if (TextUtils.isEmpty(email)) {
                    UiHelper.showSoftInput(mContext, mEmailEdit);
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    UiHelper.showSoftInput(mContext, mPasswordEdit);
                    return;
                }

                if (!StringHelper.isEmail(email)) {
                    showText(R.string.invalid_email);
                    return;
                }

                onSendRequest(email, password);
                break;
            case R.id.sign_up:
                Router.turnTo(mContext, EditUserInfoActivity.class).start();
                break;
            default:
                break;
        }
    }

    /**
     * 发送登录、注册请求
     */
    private void onSendRequest(String email, String password) {
        showProgress();
        addSubscribe(RetrofitHelper.getApis().login("login", email, password)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                hideProgress();
                if (respBean.isCodeFail()) {
                    return;
                }
                showText(respBean.getMsg());
                UserManager.setUserBean(respBean.getData());
                finish();
            }, RxHelper.getErrorTextConsumer(this)));
    }
}
