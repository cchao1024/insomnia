package com.cchao.sleep.controller;

import com.cchao.sleep.repository.FallBannerRepository;
import com.cchao.sleep.dao.FallIndex;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.service.FallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallIndexController {

    @Autowired
    FallService mFallService;
    @Autowired
    FallBannerRepository fallBannerRepository;

    @RequestMapping(value = "/fallIndex")
    public RespBean<FallIndex> getFallIndex() {
        FallIndex fallIndex = new FallIndex();
        fallIndex.setBanners(fallBannerRepository.findAll());
        fallIndex.setFallimages(mFallService.getImageByPage(0, 8).getContent());
        fallIndex.setMusic(mFallService.getMusicByPage(0, 6).getContent());
        return RespBean.suc(fallIndex);
    }
}
