package com.example.demo2.web;

import com.example.demo2.Constant;
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

    @RequestMapping(value = "/fallIndex")
    public RespBean<FallIndex> getFallIndex() {
        FallIndex fallIndex=new FallIndex();
        fallIndex.setBanners(new ArrayList<>());
        fallIndex.setFallimages(fallImageController.getImageByPage(0,10).getData());
        fallIndex.setMusic(fallMusicController.getMusicByPage(0,10).getData());
        return RespBean.suc(fallIndex);
    }
}
