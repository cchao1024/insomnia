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
public class CommentVO {

    long id;
    long postId;
    long postUserId;
    String postUserName;
    String postUserAvatar;

    long commentUserId;
    String commentUserName;
    String commentUserAvatar;

    int likeCount;
    String content;
    Date updateTime;

    int curPage;
    int totalPage;
    List<ReplyVO> list;
}
