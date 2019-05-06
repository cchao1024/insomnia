package me.cchao.insomnia.bean.resp.fall;

import lombok.Data;

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
