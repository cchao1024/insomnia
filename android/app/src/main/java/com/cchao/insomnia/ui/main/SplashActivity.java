package com.cchao.insomnia.ui.main;

import com.cchao.insomnia.BuildConfig;
import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.SplashActivityBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.GlobalHelper;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.PrefHelper;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.ui.activity.BaseStatefulActivity;

/**
 * @author : cchao
 * @version 2019-04-14
 */
public class SplashActivity extends BaseStatefulActivity<SplashActivityBinding> {
    @Override
    protected int getLayout() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initEventAndData() {
        mDataBind.version.setText("v" + BuildConfig.VERSION_NAME);
        GlobalHelper.syncAppInfo(appLaunchRespBean -> {
            toMainPage();
        }, throwable -> {
            Logs.logException(throwable);
            showText(getString(R.string.syn_info_fail));
            finish();
        });

        if (!PrefHelper.contains(Constants.Prefs.INIT_TIME_STAMP)) {
            PrefHelper.putLong(Constants.Prefs.INIT_TIME_STAMP, System.currentTimeMillis());
        }
    }

    void toMainPage() {
        RxHelper.timerConsumer(1000, consumer -> {
            Router.turnTo(mContext, MainActivity.class).start();
            finish();
        });
    }

    @Override
    protected void onLoadData() {

    }
}
