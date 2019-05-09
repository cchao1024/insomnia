package me.cchao.insomnia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import me.cchao.insomnia.bean.req.PageDTO;
import me.cchao.insomnia.bean.resp.RespListBean;
import me.cchao.insomnia.business.ImagePathConvert;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.FallMusic;
import me.cchao.insomnia.repository.FallImageRepository;
import me.cchao.insomnia.repository.FallMusicRepository;

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
        Page<FallImage> page = mImageRepository.findAll(pageDTO.toPageable());

        List<FallImage> list = page.getContent().stream()
            .map(fallImage -> {
                fallImage.setSrc(ImagePathConvert.joinRemotePath(fallImage.getSrc()));
                return fallImage;
            }).collect(Collectors.toList());

        return RespListBean.of(page, list, pageDTO.getPage());
    }

    public RespListBean<FallMusic> getMusicByPage(PageDTO pageDTO) {
        Page<FallMusic> page = mMusicRepository.findAll(pageDTO.toPageable());

        List<FallMusic> list = page.getContent().stream()
            .map(music -> {
                music.setSrc(ImagePathConvert.joinRemotePath(music.getSrc()));
                return music;
            }).collect(Collectors.toList());
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
