package com.cchao.insomnia.model.javabean.home;

import android.support.annotation.StringRes;

import com.cchao.simplelib.core.UiHelper;

/**
 * 抽屉Menu的实体
 */
public class NavItem {

    public int ID;
    public int mIconRes;

    public String mLabelText;
    public String mValueText;
    private Margin mMargin;

    public enum Margin {
        top, bottom, none
    }

    /**
     * 设置menu参数
     *
     * @param id      view 的id
     * @param title   选项文本
     * @param iconRes 图片资源ID
     * @param margin  1 上边距，2 下边距
     */
    public NavItem(int id, String title, int iconRes, Margin margin) {
        this.ID = id;
        this.mLabelText = title;
        this.mIconRes = iconRes;
        mMargin = margin;
    }

    /**
     * 设置menu参数
     *
     * @param id      view 的id
     * @param title   选项文本
     * @param iconRes 图片资源ID
     * @param margin  1 上边距，2 下边距
     */
    public static NavItem of(int id, String title, int iconRes, Margin margin) {
        return new NavItem(id, title, iconRes, margin);
    }

    public static NavItem of(int id, @StringRes int title, int iconRes, Margin margin) {
        return new NavItem(id, UiHelper.getString(title), iconRes, margin);
    }

    public Margin getMargin() {
        return mMargin;
    }

    public NavItem setMargin(Margin margin) {
        mMargin = margin;
        return this;
    }

}
