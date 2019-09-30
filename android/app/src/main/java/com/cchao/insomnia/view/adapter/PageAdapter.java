package com.cchao.insomnia.view.adapter;

import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.simplelib.core.CollectionHelper;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.interfaces.BaseStateView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 整合dataBinding的抽象页面加载Adapter，
 *
 * @author cchao
 * @version 8/11/18.
 */
public abstract class PageAdapter<T> extends DataBindQuickAdapter<T> {

    int mCurPage = 1;
    CompositeDisposable mDisposable;
    BaseStateView mStateView;

    public PageAdapter(int layoutResId, CompositeDisposable disposable, BaseStateView stateView) {
        super(layoutResId);
        mDisposable = disposable;
        mStateView = stateView;
        init();
    }

    protected abstract Observable<RespListBean<T>> getLoadObservable(int page);

    protected void init() {
        setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadData(++mCurPage);
            }
        });
    }

    public void onLoadData(int page) {
        mDisposable.add(getLoadObservable(page)
            .compose(RxHelper.toMain())
            .subscribe(new Consumer<RespListBean<T>>() {
                @Override
                public void accept(RespListBean<T> respBean) throws Exception {
                    solvePage(page, respBean);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Logs.logException(throwable);
                    if (page == 1) {
                        mStateView.switchView(CollectionHelper.isEmpty(getData())
                            ? BaseStateView.EMPTY : BaseStateView.NET_ERROR);
                    } else {
                        UiHelper.showToast(throwable.getMessage());
                        loadMoreFail();
                    }
                }
            }));
    }

    public void solvePage(int page, RespListBean<T> respBean) {
        // 响应出错
        if (respBean.isCodeFail()) {
            UiHelper.showToast(respBean.getMsg());
            loadMoreFail();
            if (page == 1) {
                mStateView.switchView(BaseStateView.NET_ERROR);
            }
            return;
        }
        mStateView.switchView(BaseStateView.CONTENT);
        List<T> data = respBean.getData();

        mCurPage = respBean.getCurPage();
        if (mCurPage == 1) {
            setNewData(data);
        } else {
            addData(data);
        }
        // 是否最后一页
        if (mCurPage >= respBean.getTotalPage()) {
            loadMoreEnd();
        } else {
            loadMoreComplete();
        }
    }
}
