package com.cchao.insomnia.model.javabean.post;

import lombok.Data;

/**
 * 可以被评论和回复
 *
 * @author cchao
 * @version 2019-05-27.
 */
@Data
public class Replyable {

    int likeCount;
    boolean isLiked;
}
