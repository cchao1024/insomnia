package com.cchao.insomnia.ui.post.convert;

import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.post.CommentVO;
import com.cchao.insomnia.model.javabean.post.ReplyVO;
import com.cchao.insomnia.model.javabean.post.Replyable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-04-13
 */
@Data
@Accessors(chain = true)
public class CommentConvert implements MultiItemEntity {
    long id;
    // 发送请求时 带过去的id
    long commentId;
    long replyId;

    public static final int TYPE_COMMENT = 0;
    public static final int TYPE_REPLY = 1;
    public static final int TYPE_REPLY_Back = 2;
    // comment:0 | reply:1
    int type = TYPE_COMMENT;
    String fromUserName;
    String fromUserAvatar;
    long fromUserId;

    String toUserName;
    long toUserId;
    String content;
    int likeCount;
    Date updateTime;
    // 来源数据bean
    Replyable mSourceBean;

    public static CommentConvert fromReplyVO(ReplyVO replyVO) {
        int type = TYPE_REPLY;
        // 如何回复的id 不是 评论者id 说明他回复其他用户的回复
        if (replyVO.getToReplyId() != 0) {
            type = TYPE_REPLY_Back;
        }
        CommentConvert item2 = new CommentConvert();
        item2.setMSourceBean(replyVO);
        item2.setId(replyVO.getId())
            .setCommentId(replyVO.getCommentId())
            .setFromUserId(replyVO.getReplyUserId())
            .setFromUserName(replyVO.getReplyUserName())
            .setFromUserAvatar(replyVO.getReplyUserAvatar())
            .setToUserId(replyVO.getCommentUserId())
            .setToUserName(replyVO.getCommentUserName())
            .setType(type)
            .setReplyId(replyVO.getId())
            .setUpdateTime(replyVO.getUpdateTime())
            .setContent(replyVO.getContent())
            .setLikeCount(replyVO.getLikeCount());
        return item2;
    }

    public static CommentConvert fromCommentVo(CommentVO commentVO) {
        CommentConvert item = new CommentConvert();
        item.setMSourceBean(commentVO);
        item.setId(commentVO.getId())
            .setCommentId(commentVO.getId())
            .setToUserId(commentVO.getPostUserId())
            .setToUserName(commentVO.getPostUserName())
            .setFromUserId(commentVO.getCommentUserId())
            .setFromUserName(commentVO.getCommentUserName())
            .setFromUserAvatar(commentVO.getCommentUserAvatar())
            .setType(TYPE_COMMENT)
            .setUpdateTime(commentVO.getUpdateTime())
            .setContent(commentVO.getContent())
            .setLikeCount(commentVO.getLikeCount());
        return item;
    }

    @Override
    public int getItemType() {
        return type;
    }

    /**
     * 发送点赞请求
     *
     * @param callBack 成功回调
     */
    public void addLike(Runnable callBack) {
        String apiType = type == TYPE_COMMENT ? "comment" : "reply";
        UserManager.addLike(apiType, id, bool1 -> {
            if (bool1) {
                mSourceBean.setLiked(true);
                mSourceBean.setLikeCount(mSourceBean.getLikeCount() + 1);
            }
            if (callBack != null) {
                callBack.run();
            }
        });
    }
}
