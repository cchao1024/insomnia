package me.cchao.insomnia.api.bean.req.post;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Data
@Accessors(chain = true)
public class ReplyDTO {

    long toId;
    long replyId;
    @Length(min = 5, max = 1024, message = "大于于5，小于1024")
    String content;

    String images;
}
