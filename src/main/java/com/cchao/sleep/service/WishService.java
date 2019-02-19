package com.cchao.sleep.service;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.constant.enums.WishType;
import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.dao.WishImage;
import com.cchao.sleep.exception.CommonException;
import com.cchao.sleep.json.convertor.FallConverter;
import com.cchao.sleep.json.fall.FallImageVo;
import com.cchao.sleep.repository.FallImageRepository;
import com.cchao.sleep.repository.WishRepository;
import com.cchao.sleep.security.SecurityHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        wishImage.setUrl(optionalFallImage.get().getUrl());
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
