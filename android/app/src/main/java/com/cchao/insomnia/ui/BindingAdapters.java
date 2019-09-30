package com.cchao.insomnia.ui;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.cchao.insomnia.util.TimeHelper;

import java.util.Date;

/**
 * databinging 的自定义 属性转化
 *
 * @author : cchao
 * @version 2019-04-17
 */
public class BindingAdapters {

    @BindingAdapter(value = {"timeText"})
    public static void setTimeText(TextView textView, Date date) {
        if (date == null) {
            textView.setText("null value");
            return;
        }
        textView.setText(TimeHelper.formatymDhms(true, date.getTime()));
    }
}
