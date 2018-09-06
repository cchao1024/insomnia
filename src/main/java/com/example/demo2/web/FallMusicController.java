package com.example.demo2.web;

import com.example.demo2.Constant;
import com.example.demo2.dao.FallMusicRepository;
import com.example.demo2.entity.FallMusic;
import com.example.demo2.entity.FallMusic;
import com.example.demo2.entity.global.RespBean;
import com.example.demo2.entity.global.RespListBean;
import com.example.demo2.util.SortHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fallmusic")
public class FallMusicController {

    @Autowired
    FallMusicRepository mFallMusicRepository;

    @RequestMapping(value = "/getByPage")
    public RespListBean<FallMusic> getMusicByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        Page<FallMusic> data = mFallMusicRepository.findAll(PageRequest.of(page, pageSize, SortHelper.basicSortId()));
        RespListBean<FallMusic> respListBean = new RespListBean<>();
        respListBean.setCurPage(page);
        respListBean.setTotalPage(data.getTotalPages());
        respListBean.setData(data.getContent());
        return respListBean;
    }

    @RequestMapping(value = "/add")
    public RespBean postFallMusic(@ModelAttribute FallMusic FallMusic, @RequestParam(value = "key") String key) {
        if (StringUtils.isEmpty(key) || !key.equalsIgnoreCase("cc")) {
            return RespBean.ofError("权限验证失败");
        }
        if (FallMusic == null) {
            return RespBean.ofError(Constant.Msg.Empty_Param);
        }
        if (mFallMusicRepository.existsByName(FallMusic.getName())) {
            return RespBean.ofFail(Constant.Msg.Has_Exisits);
        }
        FallMusic.setCover_img("");
        FallMusic.setAdd_time(System.currentTimeMillis() + "");
        mFallMusicRepository.save(FallMusic);

        return RespBean.suc();
    }

    @RequestMapping(value = "/play_count")
    public RespBean updatePlayCount(@RequestParam(value = "id") long id) {
        FallMusic fallMusic = mFallMusicRepository.getOne(id);
        fallMusic.setPlay_count(fallMusic.getPlay_count() + 1);
        mFallMusicRepository.save(fallMusic);

        return RespBean.suc();
    }
}
