package me.cchao.insomnia.api.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.resp.wish.WishItem;
import me.cchao.insomnia.api.service.WishService;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.RespListBean;

@RestController
@RequestMapping(value = "/wish")
public class WishController {

    @Autowired
    WishService mWishService;

    /**
     * 图片的wish列表
     */
    @RequestMapping(value = "/list")
    @RequiresAuthentication
    public RespListBean<WishItem> getFallIndex(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        return mWishService.getWishByPage(PageDTO.of(page, pageSize));
    }

    @RequestMapping(value = "/add")
    @RequiresAuthentication
    public RespBean addWish(@RequestParam("id") long id) {
        mWishService.addWish(id);
        return RespBean.suc();
    }

    @RequestMapping(value = "/remove")
    @RequiresAuthentication
    public RespBean removeWish(@RequestParam("id") long id) {
        mWishService.remove(id);
        return RespBean.suc();
    }
}
