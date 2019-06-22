package me.cchao.insomnia.api.bean.mq;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 邮箱事件
 *
 * @author cchao
 * @version 2019-06-20.
 */
@Data
@Accessors(chain = true)
public class EmailEvent {
    int type;
    String toEmail;
    String subject;
}
