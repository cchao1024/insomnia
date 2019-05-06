package me.cchao.insomnia.controller;

import me.cchao.insomnia.bean.req.post.ReplyDTO;
import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.service.ReplyService;

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
@RequestMapping(value = "/reply")
public class ReplyController {

    @Autowired
    ReplyService mReplyService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean ReplyNew(@Valid ReplyDTO params) {
        return mReplyService.ReplyNew(params);
    }


    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mReplyService.likeReply(id);
    }
}
