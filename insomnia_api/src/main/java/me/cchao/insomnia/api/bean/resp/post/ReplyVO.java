package me.cchao.insomnia.api.bean.resp.post;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
@Accessors(chain = true)
public class ReplyVO {

    long id;
    long postId;
    long commentId;
    long commentUserId;
    long replyUserId;
    String replyUserAvatar;
    String commentUserName;

    int likeCount;
    String content;
    String images;
    Date updateTime;
}
