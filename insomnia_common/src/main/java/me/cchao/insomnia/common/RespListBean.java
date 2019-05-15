package me.cchao.insomnia.common;

import org.springframework.data.domain.Page;

import java.util.List;

import me.cchao.insomnia.common.constant.Results;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RespListBean<T> extends RespBean<List<T>> {

    int totalPage;
    int curPage;

    public RespListBean(Results results) {
        super(results);
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
