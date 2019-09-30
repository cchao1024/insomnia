package com.cchao.insomnia.ui.main;

import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.SettingsActivityBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.simplelib.core.PrefHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;

/**
 * 设置页
 *
 * @author cchao
 * @version 2019-07-08.
 */
public class SettingsActivity extends BaseTitleBarActivity<SettingsActivityBinding> implements View.OnClickListener {
    @Override
    protected int getLayout() {
        return R.layout.settings_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(R.string.settings);
        mDataBind.setClick(this);
        String playMode = PrefHelper.getString(Constants.Prefs.Play_Mode, Constants.Play_Mode.RANDOM);
        mDataBind.playModeClick.setText(playMode);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_mode_click:
                String[] arr = new String[]{Constants.Play_Mode.LIST_LOOP, Constants.Play_Mode.RANDOM, Constants.Play_Mode.SINGLE_LOOP};

                UiHelper.showItemsDialog(mContext, UiHelper.getString(R.string.select_play_mode), arr, (dialog, which) -> {
                    PrefHelper.putString(Constants.Prefs.Play_Mode, arr[which]);
                    mDataBind.playModeClick.setText(arr[which]);
                    showText(arr[which]);
                });
                break;
        }
    }
}
