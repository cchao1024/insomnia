package com.cchao.sleep.json;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.util.SortHelper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class RespListBean<T> extends RespBean<List<T>> {

    int totalPage;
    int curPage;

    public RespListBean(Results results) {
        super(results);
    }

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

    public static <T> RespListBean<T> of(Page<T> pageObj, int curPage) {
        return RespListBean.of(pageObj, pageObj.getContent(), curPage);
    }

    public static <T> RespListBean<T> of(Page pageObj, List<T> data, int curPage) {
        RespListBean<T> respListBean = new RespListBean<>(Results.SUC);
        respListBean.setCurPage(curPage);
        respListBean.setTotalPage(pageObj.getTotalPages());
        respListBean.setData(data);
        return respListBean;
    }
}
