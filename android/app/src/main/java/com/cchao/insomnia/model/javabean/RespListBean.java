package com.cchao.insomnia.model.javabean;

import java.util.List;

public class RespListBean<T> extends RespBean<List<T>> {

    int totalPage;
    int curPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
