package com.cchao.sleep.controller;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.constant.enums.WishType;
import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.dao.FallMusic;
import com.cchao.sleep.exception.CommonException;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.json.RespListBean;
import com.cchao.sleep.json.fall.FallImageVo;
import com.cchao.sleep.json.fall.FallIndex;
import com.cchao.sleep.json.fall.FallMusicVo;
import com.cchao.sleep.service.FallService;
import com.cchao.sleep.service.WishService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Fall的控制器，包含 图片和音频
 */
@RestController
@RequestMapping(value = "/wish")
public class WishController {

    @Autowired
    WishService mWishService;

    /**
     * 图片的wish列表
     */
    @RequestMapping(value = "/getImageList")
    @RequiresAuthentication
    public RespListBean<FallImageVo> getFallIndex(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        Page<FallImageVo> pageObj = mWishService.getWishImageByPage(PageRequest.of(page, pageSize));
        return RespListBean.of(pageObj, pageObj.getContent(), page);
    }

    @RequestMapping(value = "/addwish")
    @RequiresAuthentication
    public RespBean postFallImage(@RequestParam("type") int type, @RequestParam("wish_id") long wishId) {
        // 1 ： image
        // 2 ： music
        WishType wishType = WishType.IMAGE;
        switch (type) {
            case 2:
                wishType = WishType.MUSIC;
                break;
        }
        mWishService.addWish(wishType, wishId);
        return RespBean.suc();
    }
}
