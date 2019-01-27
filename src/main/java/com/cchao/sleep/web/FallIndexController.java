package com.cchao.sleep.web;

import com.cchao.sleep.repository.FallBannerRepository;
import com.cchao.sleep.dao.FallIndex;
import com.cchao.sleep.json.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
