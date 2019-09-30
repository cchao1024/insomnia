package com.cchao.insomnia.model.javabean.post;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
public class PostVO extends Replyable {

    long id;
    long postUserId;
    String content;
    String postUserName;
    String postUserAvatar;
    List<String> imageList;
    Date updateTime;
    Date createTime;

    int curPage;
    int totalPage;
    List<CommentVO> list;
}
