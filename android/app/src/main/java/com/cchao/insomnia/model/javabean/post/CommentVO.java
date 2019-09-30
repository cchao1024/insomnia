package com.cchao.insomnia.model.javabean.post;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
public class CommentVO extends Replyable {

    long id;
    long postId;
    long postUserId;
    String postUserName;
    String postUserAvatar;

    long commentUserId;
    String commentUserName;
    String commentUserAvatar;

    String content;
    String images;
    Date updateTime;

    int curPage;
    int totalPage;
    List<ReplyVO> list;
}
