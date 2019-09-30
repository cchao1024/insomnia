package com.cchao.insomnia.view.wish;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.WishViewBinding;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.util.CallBacks;

/**
 * 点赞 按钮
 *
 * @author cchao
 * @version 2019-05-10.
 */
public class WishView extends RelativeLayout {
    public static final int DIRECT_LEFT = 1;
    public static final int DIRECT_TOP = 2;
    public static final int DIRECT_RIGHT = 3;
    public static final int DIRECT_BOTTOM = 4;
    WishViewBinding mBinding;
    boolean mIsLiked = false;
    CallBacks.Bool mCallBack;
    int mDirection = DIRECT_TOP;
    int mLikeNum = 0;
    Drawable mLikedDrawable = UiHelper.getDrawable(R.drawable.ic_wish_ed);
    Drawable mUnLikeDrawable = UiHelper.getDrawable(R.drawable.ic_wish_grey);

    public WishView(Context context) {
        this(context, null);
    }

    public WishView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WishView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wish_view, this, true);
        mBinding.setClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLike(mIsLiked);
            }
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WishView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int id = a.getIndex(i);
            switch (id) {
                case R.styleable.WishView_wish_direction:
                    mDirection = a.getInteger(id, 1);
                    break;
                case R.styleable.WishView_wish_num:
                    mLikeNum = (a.getInt(id, 0));
                    break;
                case R.styleable.WishView_wish_toggle:
                    mIsLiked = a.getBoolean(id, false);
                    break;
            }
        }
        a.recycle();
        updateToggle(mIsLiked, mLikeNum);
    }

    public WishView setDrawablePair(Drawable likedDrawable, Drawable unLikeDrawable) {
        mLikedDrawable = likedDrawable;
        mUnLikeDrawable = unLikeDrawable;
        updateToggle(mIsLiked, mLikeNum);
        return this;
    }

    public WishView setDirection(int direction) {
        mDirection = direction;
        updateToggle(mIsLiked, mLikeNum);
        return this;
    }

    public WishView setNum(int num) {
        mLikeNum = num;
        updateToggle(mIsLiked, mLikeNum);
        return this;
    }

    public WishView updateDrawable(boolean toggle) {
        Drawable top = null, left = null, right = null, bottom = null;
        Drawable drawable = toggle ? mLikedDrawable : mUnLikeDrawable;
        switch (mDirection) {
            case DIRECT_LEFT:
                left = drawable;
                break;
            case DIRECT_RIGHT:
                right = drawable;
                break;
            case DIRECT_TOP:
                top = drawable;
                break;
            case DIRECT_BOTTOM:
                bottom = drawable;
                break;
        }
        mBinding.text.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    public WishView updateToggle(boolean toggle, int num) {
        updateDrawable(toggle);
        mBinding.text.setText(String.valueOf(num));

        UiHelper.setVisibleElseInVisible(mBinding.toggleWish, true);
        UiHelper.setVisibleElseInVisible(mBinding.progress, false);
        mIsLiked = toggle;
        return this;
    }

    public WishView setWishCallBack(CallBacks.Bool callBack) {
        mCallBack = callBack;
        return this;
    }

    private void toggleLike(boolean toggle) {
        if (mCallBack != null) {
            mCallBack.onCallBack(toggle);
        }
        UiHelper.setVisibleElseInVisible(mBinding.toggleWish, false);
        UiHelper.setVisibleElseInVisible(mBinding.progress, true);
    }
}
