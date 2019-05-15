package me.cchao.insomnia.api.bean.resp.post;

import java.util.Date;
import java.util.List;

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
    List<String> imageList;
    Date updateTime;
}
