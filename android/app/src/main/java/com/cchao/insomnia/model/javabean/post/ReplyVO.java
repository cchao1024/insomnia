package com.cchao.insomnia.model.javabean.post;

import java.util.Date;

import lombok.Data;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
public class ReplyVO extends Replyable {

    long id;
    long postId;
    long commentId;
    long commentUserId;
    long toUserId;
    long toReplyId;
    long replyUserId;
    String replyUserName;
    String replyUserAvatar;
    String commentUserName;

    String content;
    String images;
    Date updateTime;
}
