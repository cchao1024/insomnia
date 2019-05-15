package me.cchao.insomnia.api.bean.req.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户注册
 *
 * @author Jcxcc
 * @date 2019-02-23
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserSignUpDTO extends UserLoginDTO {

}
