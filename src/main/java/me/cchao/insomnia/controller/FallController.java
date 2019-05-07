package me.cchao.insomnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import me.cchao.insomnia.bean.req.PageDTO;
import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.bean.resp.RespListBean;
import me.cchao.insomnia.bean.resp.fall.FallIndex;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.FallMusic;
import me.cchao.insomnia.service.FallService;

/**
 * Fall的控制器，包含 图片和音频
 */
@RestController
@RequestMapping(value = "/fall")
public class FallController {

    @Autowired
    FallService mFallService;

    /**
     * 首页数据
     */
    @RequestMapping(value = "/index")
    public RespBean<FallIndex> getFallIndex() {
        PageDTO pageDTO = PageDTO.of(1, 10);
        FallIndex fallIndex = new FallIndex()
            .setFallImages(mFallService.getImageByPage(pageDTO).getData())
            .setMusic(mFallService.getMusicByPage(pageDTO).getData());
        return RespBean.suc(fallIndex);
    }

    @RequestMapping(value = "/image/list")
    public RespListBean<FallImage> getImageByPage(@Valid PageDTO page) {
        return mFallService.getImageByPage(page);
    }

    @RequestMapping(value = "/image/add")
    public RespBean addFallImage(FallImage fallImage) {
        mFallService.save(fallImage);
        return RespBean.suc();
    }

    /**
     * music
     */
    @RequestMapping(value = "/music/add")
    public RespBean addFallMusic(FallMusic fallMusic) {
        mFallService.save(fallMusic);
        return RespBean.suc();
    }

    @RequestMapping(value = "/music/list")
    public RespListBean<FallMusic> getMusicByPage(@Valid PageDTO page) {
        return mFallService.getMusicByPage(page);
    }

    @RequestMapping(value = "/music/play")
    public RespBean updatePlayCount(@RequestParam long id) {
        mFallService.onMusicPlayed(id);
        return RespBean.suc();
    }
}
