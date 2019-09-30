package com.cchao.insomnia.manager;

import android.os.CountDownTimer;

import com.cchao.insomnia.global.Constants;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.UiHelper;

/**
 * 倒计时关闭管理
 *
 * @author cchao
 * @version 2019-05-09.
 */
public class TimeCountHelper {
    static CountDownTimer mCountDownTimer;
    public static String mCurCountTimeStr;

    /**
     * 开启计时
     *
     * @param second 秒
     */
    public static void startCountDown(int second, Runnable finishCallBack) {
        mCountDownTimer = new CountDownTimer(second * 1000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                int convertTime = (int) (millisUntilFinished / 1000) - 1;
                if (convertTime <= 0) {
                    mCountDownTimer.cancel();
                    if (finishCallBack != null) {
                        UiHelper.runOnUiThread(finishCallBack);
                    }
                    return;
                }

                int hourTime = convertTime % (24 * 60 * 60) / 3600;
                int minuteTime = convertTime % 3600 / 60;
                int secondTime = convertTime % 60;

                String result = "";
                if (hourTime != 0) {
                    result += hourTime + ":";
                }
                if (minuteTime != 0) {
                    result += minuteTime + ":";
                }
                result += secondTime;

                mCurCountTimeStr = result;
                RxBus.get().postEvent(Constants.Event.update_count_down, result);
            }

            @Override
            public void onFinish() {
                cancel();
                if (finishCallBack != null) {
                    UiHelper.runOnUiThread(finishCallBack);
                }
            }
        };
        mCountDownTimer.start();
    }

    public static void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCurCountTimeStr = "";
    }
}
