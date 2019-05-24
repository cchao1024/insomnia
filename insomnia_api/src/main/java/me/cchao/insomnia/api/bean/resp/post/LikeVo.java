package me.cchao.insomnia.api.bean.resp.post;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 点赞后的返回
 * @author cchao
 * @version 2019-05-24.
 */
@Data
@Accessors(chain = true)
public class LikeVo {
    int likedNum;
}
