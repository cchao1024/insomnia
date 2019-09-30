package me.cchao.insomnia.api.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.bean.req.post.PostDTO;
import me.cchao.insomnia.api.bean.resp.post.PostListVO;
import me.cchao.insomnia.api.bean.resp.post.PostVO;
import me.cchao.insomnia.api.bean.resp.RespBean;
import me.cchao.insomnia.api.bean.resp.RespListBean;
import me.cchao.insomnia.api.service.PostService;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    PostService mPostService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean postNew(@Valid PostDTO params) {
        return mPostService.postNew(params);
    }

    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mPostService.likePost(id);
    }

    /**
     * 获取默认第一页
     */
    @RequestMapping("/index")
    public RespListBean<PostVO> getIndex(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<PostVO> pageObj = mPostService.getIndex(PageDTO.of(1, pageSize));
        return RespListBean.of(pageObj, 0);
    }

    @RequestMapping("/list")
    public RespListBean<PostListVO> getPostByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        return mPostService.getPostByPage(PageDTO.of(page, pageSize));
    }

    @RequestMapping("/detail")
    public RespBean getPostById(Long id) {
        PostVO postVO = mPostService.findPostVo(id, PageDTO.of(1, 10));
        return RespBean.suc(postVO);
    }
}
