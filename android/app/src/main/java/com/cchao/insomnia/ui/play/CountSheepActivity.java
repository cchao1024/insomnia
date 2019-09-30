package com.cchao.insomnia.ui.play;

import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.CountSheepActivityBinding;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;

import org.apache.commons.lang3.RandomUtils;

import java.util.Locale;

/**
 * 数绵羊
 *
 * @author cchao
 * @version 2019-05-22.
 */
public class CountSheepActivity extends BaseTitleBarActivity<CountSheepActivityBinding> implements View.OnClickListener {
    int mCountNum;
    long mStartTimeStamp = System.currentTimeMillis();
    boolean mHasBackPress;

    @Override
    protected int getLayout() {
        return R.layout.count_sheep_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(R.string.count_sleep);
        mDataBind.setClick(this);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onBackPressed() {
        if (mHasBackPress) {
            super.onBackPressed();
            return;
        }
        mHasBackPress = true;
        long millisSecond = (System.currentTimeMillis() - mStartTimeStamp);
        int second = (int) (millisSecond / 1000);
        String averSecond = String.format(Locale.getDefault(), "%.2f", millisSecond * 1.0f / mCountNum / 1000f);
        String msg = String.format(UiHelper.getString(R.string.count_result_text), mCountNum, second, averSecond);
        UiHelper.showConfirmDialog(mContext, msg, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            super.onBackPressed();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sheep:
                changeLayout();
                if (mCountNum == 0) {
                    mStartTimeStamp = System.currentTimeMillis();
                }
                mCountNum++;
                break;
        }
    }

    void changeLayout() {
        int viewWidth = UiHelper.dp2px(60);
        int x = RandomUtils.nextInt(viewWidth, UiHelper.getScreenWidth() - viewWidth);
        int y = RandomUtils.nextInt(viewWidth, UiHelper.getScreenHeight() - viewWidth - UiHelper.dp2px(100));

        // 先透明下 200毫秒后 又显示
        int alphaDuration = 100;
        int hideDuration = 40;
        mDataBind.sheep.animate().alpha(0.5f).setDuration(alphaDuration).withEndAction(new Runnable() {
            @Override
            public void run() {
                UiHelper.setVisibleElseGone(mDataBind.sheep, false);
            }
        }).start();
        mDataBind.sheep.animate().alpha(1f).setDuration(alphaDuration).setStartDelay(alphaDuration + hideDuration).start();
        RxHelper.timerConsumer(alphaDuration + hideDuration, aLong -> {
            UiHelper.setVisibleElseGone(mDataBind.sheep, true);
            mDataBind.sheep.layout(x, y, x + viewWidth, y + viewWidth);
        });
    }
}
