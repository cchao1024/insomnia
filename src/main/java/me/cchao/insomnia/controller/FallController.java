package me.cchao.insomnia.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.bean.resp.RespListBean;
import me.cchao.insomnia.bean.resp.fall.FallImageVo;
import me.cchao.insomnia.bean.resp.fall.FallIndex;
import me.cchao.insomnia.bean.resp.fall.FallMusicVo;
import me.cchao.insomnia.constant.enums.Results;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.FallMusic;
import me.cchao.insomnia.exception.CommonException;
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
        FallIndex fallIndex = new FallIndex();
//        fallIndex.setBanners(fallBannerRepository.findAll());
        fallIndex.setFallImages(mFallService.getImageByPage(0, 8).getContent());
        fallIndex.setMusic(mFallService.getMusicByPage(0, 6).getContent());
        return RespBean.suc(fallIndex);
    }

    @RequestMapping(value = "/image/getByPage")
    public RespListBean<FallImageVo> getImageByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        Page<FallImage> pageObj = mFallService.getImageByPage(page, pageSize);
        List<FallImageVo> categoryTypeList = pageObj.getContent().stream()
                .map(fallImage -> {
                    FallImageVo fallImageVo = new FallImageVo();
                    BeanUtils.copyProperties(fallImage, fallImageVo);
                    return fallImageVo;
                }).collect(Collectors.toList());

        return RespListBean.of(pageObj, categoryTypeList, page);
    }

    @RequestMapping(value = "/image/add")
    public RespBean postFallImage(@ModelAttribute FallImage fallImage) {
        if (fallImage == null) {
            throw CommonException.of(Results.PARAM_EMPTY);
        }
        mFallService.save(fallImage);

        return RespBean.suc();
    }
    @RequestMapping(value = "/music/add")
    public RespBean postFallImage(@ModelAttribute FallMusic fallMusic) {
        if (fallMusic == null) {
            throw CommonException.of(Results.PARAM_EMPTY);
        }
        mFallService.save(fallMusic);

        return RespBean.suc();
    }

    @RequestMapping(value = "/music/getByPage")
    public RespListBean<FallMusicVo> getMusicByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        Page<FallMusic> pageObj = mFallService.getMusicByPage(page, pageSize);
        List<FallMusicVo> categoryTypeList = pageObj.getContent().stream()
                .map(fallImage -> {
                    FallMusicVo fallImageVo = new FallMusicVo();
                    BeanUtils.copyProperties(fallImage, fallImageVo);
                    return fallImageVo;
                }).collect(Collectors.toList());

        return RespListBean.of(pageObj, categoryTypeList, page);
    }

    @RequestMapping(value = "/music/play")
    public RespBean updatePlayCount(@RequestParam(value = "id") long id) {
        mFallService.onMusicPlayed(id);
        return RespBean.suc();
    }
}
