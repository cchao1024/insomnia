package com.cchao.insomnia.json.fall;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Data
public class FallMusicVo {
    private Long id;

    private String name;

    private String src;

    private String singer;

    private int play_count;

    private String cover_img;

    private long duration;
}
