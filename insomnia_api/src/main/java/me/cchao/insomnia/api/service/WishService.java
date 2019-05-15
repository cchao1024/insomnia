package me.cchao.insomnia.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.cchao.insomnia.api.bean.resp.fall.FallImageVo;
import me.cchao.insomnia.common.constant.Results;
import me.cchao.insomnia.common.constant.WishType;
import me.cchao.insomnia.api.exception.CommonException;
import me.cchao.insomnia.api.repository.FallImageRepository;
import me.cchao.insomnia.api.repository.WishRepository;
import me.cchao.insomnia.api.security.SecurityHelper;
import me.cchao.insomnia.api.domain.FallImage;
import me.cchao.insomnia.api.domain.WishImage;

/**
 * @author : cchao
 * @version 2019-02-02
 */
@Service
public class WishService {

    @Autowired
    WishRepository mWishRepository;
    @Autowired
    FallImageRepository mFallImageRepository;

    public void addWish(WishType type, Long id) {
        Optional<FallImage> optionalFallImage = mFallImageRepository.findById(id);
        if (!optionalFallImage.isPresent()) {
            throw CommonException.of(Results.FAIL);
        }
        WishImage wishImage = new WishImage();
        wishImage.setContentId(id);
        wishImage.setType(type);
        wishImage.setUrl(optionalFallImage.get().getSrc());
        wishImage.setUserId(SecurityHelper.getUserId());
        mWishRepository.save(wishImage);
    }

    public Page<FallImageVo> getWishImageByPage(Pageable pageable) {
        Page<WishImage> wishImagePage = mWishRepository.findByUserId(SecurityHelper.getUserId(), pageable);

        List<FallImageVo> fallImageVoList = wishImagePage.getContent()
                .stream().map(wishImage -> {
                    FallImageVo fallImageVo = new FallImageVo();
                    BeanUtils.copyProperties(mFallImageRepository.findById(wishImage.getId()), fallImageVo);
                    return fallImageVo;
                }).collect(Collectors.toList());

        return new PageImpl<>(fallImageVoList, pageable, wishImagePage.getTotalElements());
    }
}
