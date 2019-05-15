package me.cchao.insomnia.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.req.post.PostDTO;
import me.cchao.insomnia.api.business.ImagePathConvert;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Results;
import me.cchao.insomnia.api.bean.resp.post.CommentVO;
import me.cchao.insomnia.api.bean.resp.post.PostListVO;
import me.cchao.insomnia.api.bean.resp.post.PostVO;
import me.cchao.insomnia.api.domain.Post;
import me.cchao.insomnia.api.domain.User;
import me.cchao.insomnia.api.exception.CommonException;
import me.cchao.insomnia.api.repository.PostRepository;
import me.cchao.insomnia.api.security.SecurityHelper;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Service
public class PostService {

    @Autowired
    PostRepository mPostRepository;
    @Autowired
    UserService mUserService;
    @Autowired
    CommentService mCommentService;

    public Post findById(long id) {
        return mPostRepository.getOne(id);
    }

    public RespBean postNew(PostDTO dto) {

        Post post = new Post();
        BeanUtils.copyProperties(dto, post);

        post.setUserId(SecurityHelper.getUserId());

        mPostRepository.save(post);
        return RespBean.suc();
    }

    /**
     * 获取评论下的 部分回复
     *
     * @param postId toId
     * @param dto    page
     */
    public PostVO findPostVo(long postId, PageDTO dto) {
        Optional<Post> optional = mPostRepository.findById(postId);
        if (!optional.isPresent()) {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
        Post post = optional.get();
        // copy到vo
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);

        // 获取用户信息
        User postUser = mUserService.findUserById(post.getUserId());

        // 获取 CommentVo，
        Page<CommentVO> commentVO = mCommentService.findCommentVoByPost(post.getId(), dto);

        postVO.setPostUserId(postUser.getId())
            .setPostUserAvatar(postUser.getAvatar())
            .setPostUserName(postUser.getNickName())
            .setImageList(ImagePathConvert.convertImageList(post.getImages()))
            // list comment
            .setList(commentVO.getContent())
            .setCurPage(dto.getPage())
            .setTotalPage(commentVO.getTotalPages());
        return postVO;
    }

    public Page<PostVO> getIndex(PageDTO dto) {
        Page<PostVO> result = mPostRepository.findAll(dto.toPageable()).map(post -> {
            return findPostVo(post.getId(), PageDTO.of(0, 10));
        });
        return result;
    }

    public Page<PostListVO> findPostList(PageDTO dto) {
        Page<PostListVO> result = mPostRepository.findAll(dto.toPageable()).map(post -> {
            User user = mUserService.findUserById(post.getUserId());

            // postListVO
            PostListVO postListVO = new PostListVO();
            BeanUtils.copyProperties(post, postListVO);
            postListVO.setPostUserAvatar(user.getAvatar())
                .setPostUserName(user.getNickName())
                .setImageList(ImagePathConvert.convertImageList(post.getImages()))
                .setPostUserId(user.getId());

            return postListVO;
        });
        return result;
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     *
     * @param id id
     */
    public RespBean likePost(Long id) {
        Optional<Post> optional = mPostRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            mPostRepository.save(post.increaseLike());

            // 用户 like +1
            mUserService.increaseLike(post.getUserId());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
    }

    /**
     * 添加评论
     *
     * @param id id
     */
    public RespBean reviewPost(Long id) {
        Optional<Post> optional = mPostRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            mPostRepository.save(post.increaseReview());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
    }
}
