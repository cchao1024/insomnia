package com.cchao.insomnia.repository;

import com.cchao.insomnia.dao.FallImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author : cchao
 * @version 2019-02-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FallImageRepositoryTest {

    @Autowired
    FallImageRepository fallImageRepository;

    @Test
    public void updateTest() {
        FallImage fallImage = fallImageRepository.findById(3L).get();
        log.debug(fallImage.getUrl());
        fallImage.setHeight(fallImage.getHeight()+10000);
        FallImage result = fallImageRepository.save(fallImage);
        Assert.assertNotNull(result);

    }

    @Test
    public void saveTest() {
        FallImage fallImage = new FallImage();
        fallImage.setHeight(9999);
        fallImage.setWidth(9999);
        fallImage.setUrl("xxx");
        FallImage result = fallImageRepository.save(fallImage);
        Assert.assertNotNull(result);

    }

}