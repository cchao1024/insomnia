package me.cchao.insomnia.controller;

import me.cchao.insomnia.bean.req.post.CommentDTO;
import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.service.CommentService;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    CommentService mCommentService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean CommentNew(@Valid CommentDTO params) {
        return mCommentService.CommentNew(params);
    }


    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mCommentService.likeComment(id);
    }
}
