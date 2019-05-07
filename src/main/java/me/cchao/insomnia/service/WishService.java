package me.cchao.insomnia.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.cchao.insomnia.bean.resp.fall.FallImageVo;
import me.cchao.insomnia.constant.enums.Results;
import me.cchao.insomnia.constant.enums.WishType;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.WishImage;
import me.cchao.insomnia.exception.CommonException;
import me.cchao.insomnia.repository.FallImageRepository;
import me.cchao.insomnia.repository.WishRepository;
import me.cchao.insomnia.security.SecurityHelper;

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
