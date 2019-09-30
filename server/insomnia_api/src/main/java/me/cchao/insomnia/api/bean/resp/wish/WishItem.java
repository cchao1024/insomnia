package me.cchao.insomnia.api.bean.resp.wish;

import java.util.Date;

import lombok.Data;

/**
 * @author cchao
 * @version 2019-05-23.
 */
@Data
public class WishItem {
    long id;
    long wishId;
    /**
     * 1 fall_image
     * 2 fall_music
     * ...
     */
    int type;
    Date updateTime;

    /**
     * 被收藏的实体
     */
    Object content;
}
