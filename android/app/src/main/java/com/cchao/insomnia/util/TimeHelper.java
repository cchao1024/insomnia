package com.cchao.insomnia.util;

import com.cchao.insomnia.R;
import com.cchao.simplelib.core.UiHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : cchao
 * @version 2019-04-23
 */
public class TimeHelper {

    public static String getStandardDate(Date date) {
        if (date == null) {
            return getStandardDate(System.currentTimeMillis());
        }
        return getStandardDate(date.getTime());
    }

    public static String formatymD(boolean needSeparator, long timeStamp) {
        String format = "yyyyMMdd";
        if (needSeparator) {
            format = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(format).format(new Date(timeStamp));
    }

    public static String formatymDhms(boolean needSeparator, long timeStamp) {
        String format = "yyyyMMddHHmmss";
        if (needSeparator) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(new Date(timeStamp));
    }

    /**
     * 返回 格式化过去表达
     *
     * @param timeStr 毫秒数
     * @return 2天前
     */
    public static String getStandardDate(long timeStr) {
        String temp = "";
        try {
            long diff = (System.currentTimeMillis() - timeStr) / 1000;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
            if (months > 0) {
                temp = months + UiHelper.getString(R.string.mouth_ago);
            } else if (days > 0) {
                temp = days + UiHelper.getString(R.string.day_ago);
            } else if (hours > 0) {
                temp = hours + UiHelper.getString(R.string.hour_ago);
            } else if (minutes > 1) {
                temp = minutes + UiHelper.getString(R.string.minute_ago);
            } else {
                temp = UiHelper.getString(R.string.now_ago);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
