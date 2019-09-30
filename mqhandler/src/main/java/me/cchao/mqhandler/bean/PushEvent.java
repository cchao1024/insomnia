package me.cchao.mqhandler.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 推送事件
 *
 * @author cchao
 * @version 2019-06-20.
 */
@Data
@Accessors(chain = true)
public class PushEvent {
    long fromUserId;
    long toUserId;
    long focusId;
    int type;
    String content;
    String title;
    String url;
}
