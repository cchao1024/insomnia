package me.cchao.insomnia.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.req.post.ReplyDTO;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Results;
import me.cchao.insomnia.api.bean.resp.post.ReplyVO;
import me.cchao.insomnia.api.domain.Comment;
import me.cchao.insomnia.api.domain.Reply;
import me.cchao.insomnia.api.domain.User;
import me.cchao.insomnia.api.exception.CommonException;
import me.cchao.insomnia.api.repository.ReplyRepository;
import me.cchao.insomnia.api.security.SecurityHelper;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Service
public class ReplyService {

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

        if (comment.getCommentUserId() == SecurityHelper.getUserId()) {
            throw CommonException.of(Results.FAIL.msg("你不能回复自己的说说"));
        }
        // comment 评论+1
        mCommentService.increaseReview(commentId);

        Reply reply = new Reply();
        BeanUtils.copyProperties(dto, reply);

        reply.setPostId(comment.getPostId())
            .setCommentId(commentId)
            .setCommentUserId(comment.getCommentUserId())
            .setReplyUserId(SecurityHelper.getUserId());

        mReplyRepository.save(reply);
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
        Page<ReplyVO> result = commentPage.map(comment -> {
            // 获取用户信息
            User replyUser = mUserService.findUserById(comment.getReplyUserId());
            User commentUser = mUserService.findUserById(comment.getCommentUserId());

            // 封装 vo
            ReplyVO replyVO = new ReplyVO();
            BeanUtils.copyProperties(comment, replyVO);
            replyVO.setReplyUserId(replyUser.getId())
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
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_COMMENT);
        }
    }
}
