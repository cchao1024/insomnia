package me.cchao.insomnia.bean.resp.post;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
@Accessors(chain = true)
public class PostListVO {

    long id;
    long postUserId;
    int reviewCount;
    int likeCount;
    String content;
    String postUserName;
    String postUserAvatar;
    String images;
    Date updateTime;
}
