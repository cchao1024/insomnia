package com.cchao.insomnia.view.wish;

/**
 * @author cchao
 * @version 2019-05-27.
 */
public class BindingAdapter {

    @android.databinding.BindingAdapter(value = {"wish_num", "wish_toggle"}, requireAll = false)
    public static void setWishNum(WishView view, int num, boolean liked) {
        view.updateToggle(liked, num);
    }
}
