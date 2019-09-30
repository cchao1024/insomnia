package com.cchao.insomnia.model.javabean.post;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
public class PostListVO {

    long id;
    long postUserId;
    int reviewCount;
    int likeCount;
    boolean isLiked;

    String content;
    String postUserName;
    String postUserAvatar;
    List<String> imageList;
    Date updateTime;
}
