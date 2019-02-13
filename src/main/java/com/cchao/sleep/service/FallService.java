package com.cchao.sleep.service;

import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.dao.FallMusic;
import com.cchao.sleep.repository.FallImageRepository;
import com.cchao.sleep.repository.FallMusicRepository;
import com.cchao.sleep.util.SortHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Page<FallImage> getImageByPage(int page, int pageSize) {
        Page<FallImage> data = mImageRepository.findAll(PageRequest.of(page, pageSize, SortHelper.basicSortId()));
        return data;
    }

    public FallImage save(FallImage fallImage) {
        return mImageRepository.save(fallImage);
    }

    public Page<FallMusic> getMusicByPage(int page, int pageSize) {
        Page<FallMusic> data = mMusicRepository.findAll(PageRequest.of(page, pageSize, SortHelper.basicSortId()));
        return data;
    }

    public FallMusic save(FallMusic fallImage) {
        return mMusicRepository.save(fallImage);
    }

    /**
     * 点击播放 播放数量+1
     * @param id id
     */
    public void onMusicPlayed(long id) {
        FallMusic fallMusic = mMusicRepository.getOne(id);
        fallMusic.setPlay_count(fallMusic.getPlay_count() + 1);
        mMusicRepository.save(fallMusic);
    }
}
