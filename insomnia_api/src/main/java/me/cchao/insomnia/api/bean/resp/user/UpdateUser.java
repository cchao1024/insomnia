package me.cchao.insomnia.api.bean.resp.user;

import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.api.domain.User;

/**
 * 更新用户信息，返回用户实体和新的token
 */
@Data
@Accessors(chain = true)
public class UpdateUser extends User {
    String token;
}
