package me.cchao.insomnia.bean.resp.post;

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
public class PostVO {

    long id;
    long postUserId;
    int likeCount;
    String content;
    String postUserName;
    String postUserAvatar;
    List<String> imageList;
    Date updateTime;

    int curPage;
    int totalPage;
    List<CommentVO> list;
}
