package me.cchao.insomnia.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.resp.wish.WishItem;
import me.cchao.insomnia.api.business.ImagePathConvert;
import me.cchao.insomnia.api.config.GlobalConfig;
import me.cchao.insomnia.api.domain.Wish;
import me.cchao.insomnia.api.repository.WishRepository;
import me.cchao.insomnia.api.security.SecurityHelper;
import me.cchao.insomnia.common.RespListBean;
import me.cchao.insomnia.common.constant.Constant;

/**
 * @author : cchao
 * @version 2019-02-02
 */
@Service
public class WishService {

    @Autowired
    WishRepository mWishRepository;
    @Autowired
    FallService mFallService;

    public void addWish(Long id) {
        Wish wish = new Wish();
        wish.setWishId(id);
        wish.setUserId(SecurityHelper.getUserId());
        mWishRepository.save(wish);
    }

    public void remove(Long id) {
        mWishRepository.deleteById(id);
    }

    public RespListBean<WishItem> getWishByPage(PageDTO pageable) {
        Page<Wish> page = mWishRepository.findByUserId(SecurityHelper.getUserId(), pageable.toPageIdDesc());

        List<WishItem> wishItems = page.getContent()
                .stream()
                .map(wish -> {
                    // 加入 wish 的实体obj
                    WishItem wishItem = new WishItem();
                    BeanUtils.copyProperties(wish, wishItem);

                    Object object = null;
                    long wishId = wish.getWishId();
                    wishItem.setType(GlobalConfig.getTypeById(wishId));
                    switch (GlobalConfig.getTypeById(wishId)) {
                        case Constant.Module.FALL_IMAGE:
                            object = mFallService.getImage(wishId);
                            break;
                        case Constant.Module.FALL_MUSIC:
                            object = mFallService.getMusic(wishId);
                            break;
                    }
                    wishItem.setContent(ImagePathConvert.joinRemotePath(object));
                    return wishItem;
                }).collect(Collectors.toList());

        return RespListBean.of(page, wishItems, pageable.getPage());
    }
}
