package me.cchao.insomnia.api.controller;

import me.cchao.insomnia.api.bean.req.post.ReplyDTO;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.api.service.ReplyService;

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
        return mReplyService.replyNew(params);
    }


    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mReplyService.likeReply(id);
    }
}
