package com.cchao.insomnia.manager;

import android.text.TextUtils;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.model.javabean.post.PostListVO;
import com.cchao.insomnia.model.javabean.post.PostVO;
import com.cchao.insomnia.model.javabean.user.UserBean;
import com.cchao.insomnia.ui.post.convert.CommentConvert;
import com.cchao.simplelib.LibCore;
import com.cchao.simplelib.core.GsonUtil;
import com.cchao.simplelib.core.PrefHelper;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.interfaces.BaseView;
import com.cchao.simplelib.util.CallBacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.disposables.Disposable;

/**
 * @author cchao
 * @version 9/9/18.
 */
public class UserManager {

    private static List<String> mWishList = new ArrayList<>();
    private static UserBean mUserBean;

    static {
        mWishList = GsonUtil.jsonToList(PrefHelper.getString(Constants.Prefs.WISH_LIST, "[]"), String.class);
        mUserBean = GsonUtil.fromJson(PrefHelper.getString(Constants.Prefs.USER_INFO), UserBean.class);
    }

    public static void processIsWish(Object object) {
        if (object instanceof PostListVO) {
            if (isInWishList(((PostListVO) object).getId())) {
                ((PostListVO) object).setLiked(true);
            }
        } else if (object instanceof CommentConvert) {
            if (isInWishList(((CommentConvert) object).getId())) {
                ((CommentConvert) object).getMSourceBean().setLiked(true);
            }
        } else if (object instanceof PostVO) {
            if (isInWishList(((PostVO) object).getId())) {
                ((PostVO) object).setLiked(true);
            }
        }
    }

    public static boolean isInWishList(long id) {
        for (String s : mWishList) {
            if (String.valueOf(id).equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static void toggleWish(long id) {
        if (!isInWishList(id)) {
            mWishList.add(String.valueOf(id));
            PrefHelper.putString(Constants.Prefs.WISH_LIST, GsonUtil.toJson(mWishList));
        }
    }

    public static void setUserBean(UserBean userBean) {
        mUserBean = userBean;
        PrefHelper.putString(Constants.Prefs.USER_INFO, GsonUtil.toJson(userBean));
        JPushInterface.setAlias(LibCore.getContext(), 123, String.valueOf(mUserBean.getId()));
        RxBus.get().post(userBean);
    }

    /**
     * 仅用于 debug模式
     */
    public static void setToken(String token) {
        if (!LibCore.isDebug()) {
            UiHelper.showToast(R.string.only_for_debug);
            return;
        }
        mUserBean.setToken(token);
        setUserBean(mUserBean);
    }


    public static UserBean getUserBean() {
        return mUserBean;
    }

    public static String getToken() {
        if (mUserBean == null) {
            return "";
        }
        return mUserBean.getToken();
    }

    public static boolean isVisitor() {
        return TextUtils.isEmpty(mUserBean.getEmail());
    }

    /**
     * 点赞
     *
     * @param type     [post,comment,reply]
     * @param id       id
     * @param callBack 回到 bool 成功与否
     */
    public static void addLike(String type, long id, CallBacks.Bool callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        Disposable disposable = RetrofitHelper.getApis().like(type, map)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                if (callBack != null) {
                    callBack.onCallBack(respBean.isCodeSuc());
                }
                if (respBean.isCodeFail()) {
                    UiHelper.showToast(respBean.getMsg());
                    return;
                }
            }, RxHelper.getErrorConsumer());
        UserManager.toggleWish(id);
    }

    public static void addWish(long id, BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        baseView.showProgress();
        Disposable disposable = RetrofitHelper.getApis().addWish(id)
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                baseView.hideProgress();
                UiHelper.showToast(respBean.getMsg());
            }, RxHelper.getErrorConsumer());
    }
}
