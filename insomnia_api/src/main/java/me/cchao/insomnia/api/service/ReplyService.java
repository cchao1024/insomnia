package me.cchao.insomnia.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.req.post.ReplyDTO;
import me.cchao.insomnia.api.bean.resp.post.ReplyVO;
import me.cchao.insomnia.api.business.MQueueHandler;
import me.cchao.insomnia.api.domain.Comment;
import me.cchao.insomnia.api.domain.Reply;
import me.cchao.insomnia.api.domain.User;
import me.cchao.insomnia.api.exception.CommonException;
import me.cchao.insomnia.api.repository.ReplyRepository;
import me.cchao.insomnia.api.security.SecurityHelper;
import me.cchao.insomnia.api.bean.resp.RespBean;
import me.cchao.insomnia.common.constant.Constant;
import me.cchao.insomnia.api.bean.resp.Results;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Service
public class ReplyService {

    @Autowired
    MQueueHandler mMqHandler;
    @Autowired
    ReplyRepository mReplyRepository;
    @Autowired
    CommentService mCommentService;
    @Autowired
    UserService mUserService;

    /**
     * 新的回复
     */
    public RespBean replyNew(ReplyDTO dto) {
        long commentId = dto.getToId();
        Comment comment = mCommentService.findById(commentId);

        // comment 评论+1
        mCommentService.increaseReview(commentId);

        Reply row = new Reply();
        BeanUtils.copyProperties(dto, row);

        row.setPostId(comment.getPostId())
                .setCommentId(commentId)
                .setCommentUserId(comment.getCommentUserId())
                .setToUserId(comment.getCommentUserId())
                .setReplyUserId(SecurityHelper.getUserId());

        // 不为0，表示 回复用户
        if (0 != dto.getReplyId()) {
            Reply fromReply = mReplyRepository.findById(dto.getReplyId()).get();
            row.setToUserId(fromReply.getReplyUserId())
                    .setToReplyId(dto.getReplyId());
        }

        mReplyRepository.save(row);
        return RespBean.suc();
    }

    /**
     * 获取评论下的 部分回复
     *
     * @param dto
     * @return
     */
    public Page<ReplyVO> findReplyVoByComment(long commentId, PageDTO dto) {
        // 拿到comment 分页
        Page<Reply> commentPage = mReplyRepository.findByCommentId(commentId, dto.toPageable());
        // 转化成 VO
        Page<ReplyVO> result = commentPage.map(reply -> {
            // 获取用户信息
            User replyUser = mUserService.findUserById(reply.getReplyUserId());
            User commentUser = mUserService.findUserById(reply.getToUserId());

            // 封装 vo
            ReplyVO replyVO = new ReplyVO();
            BeanUtils.copyProperties(reply, replyVO);
            replyVO.setReplyUserId(replyUser.getId())
                    .setReplyUserAvatar(replyUser.getAvatar())
                    .setReplyUserName(replyUser.getNickName())
                    .setToReplyId(reply.getToReplyId())
                    .setToUserId(reply.getToUserId())
                    .setCommentUserId(commentUser.getId())
                    .setCommentUserName(commentUser.getNickName());
            return replyVO;
        });
        return result;
    }

    public Page<Reply> getReplyList(PageDTO dto) {
        return mReplyRepository.findAll(dto.toPageable());
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     *
     * @param id id
     */
    public RespBean likeReply(Long id) {
        Optional<Reply> optional = mReplyRepository.findById(id);
        if (optional.isPresent()) {
            Reply reply = optional.get();
            mReplyRepository.save(reply.increaseLike());

            mUserService.increaseLike(reply.getReplyUserId());
            // 加入推送队列
            mMqHandler.pushLikeEvent(Constant.POST_TYPE.Reply, id, reply.getReplyUserId());

            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_COMMENT);
        }
    }
}
