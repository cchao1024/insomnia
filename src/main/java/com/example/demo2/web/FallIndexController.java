package com.example.demo2.web;

import com.example.demo2.Constant;
import com.example.demo2.dao.FallBannerRepository;
import com.example.demo2.entity.FallMusic;
import com.example.demo2.entity.FallIndex;
import com.example.demo2.entity.global.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FallIndexController {

    @Autowired
    FallMusicController fallMusicController;
    @Autowired
    FallImageController fallImageController;
    @Autowired
    FallBannerRepository fallBannerRepository;

    @RequestMapping(value = "/fallIndex")
    public RespBean<FallIndex> getFallIndex() {
        FallIndex fallIndex = new FallIndex();
        fallIndex.setBanners(fallBannerRepository.findAll());
        fallIndex.setFallimages(fallImageController.getImageByPage(0, 8).getData());
        fallIndex.setMusic(fallMusicController.getMusicByPage(0, 6).getData());
        return RespBean.suc(fallIndex);
    }
}
