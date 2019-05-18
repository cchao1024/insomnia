package me.cchao.insomnia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.business.ImagePathConvert;
import me.cchao.insomnia.common.RespListBean;
import me.cchao.insomnia.api.repository.FallImageRepository;
import me.cchao.insomnia.api.repository.FallMusicRepository;
import lombok.extern.slf4j.Slf4j;
import me.cchao.insomnia.api.domain.FallImage;
import me.cchao.insomnia.api.domain.FallMusic;

/**
 * @author : cchao
 * @version 1/30/19
 */
@Service
@Slf4j
public class FallService {

    @Autowired
    FallImageRepository mImageRepository;
    @Autowired
    FallMusicRepository mMusicRepository;

    public RespListBean<FallImage> getImageByPage(PageDTO pageDTO) {
        Page<FallImage> page = mImageRepository.findAll(pageDTO.toPageIdDesc());

        List<FallImage> list = page.getContent().stream()
            .map(ImagePathConvert::joinRemotePath)
            .collect(Collectors.toList());

        return RespListBean.of(page, list, pageDTO.getPage());
    }

    public RespListBean<FallMusic> getMusicByPage(PageDTO pageDTO) {
        Page<FallMusic> page = mMusicRepository.findAll(pageDTO.toPageable());

        List<FallMusic> list = page.getContent().stream()
            .map(ImagePathConvert::joinRemotePath)
            .collect(Collectors.toList());
        return RespListBean.of(page, list, pageDTO.getPage());
    }

    public FallImage save(FallImage fallImage) {
        return mImageRepository.save(fallImage);
    }

    public FallMusic save(FallMusic music) {
        return mMusicRepository.save(music);
    }

    /**
     * 点击播放 播放数量+1
     *
     * @param id id
     */
    public void onMusicPlayed(long id) {
        FallMusic fallMusic = mMusicRepository.getOne(id);
        fallMusic.setPlay_count(fallMusic.getPlay_count() + 1);
        mMusicRepository.save(fallMusic);
    }
}
