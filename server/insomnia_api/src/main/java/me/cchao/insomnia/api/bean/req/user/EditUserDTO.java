package me.cchao.insomnia.api.bean.req.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.cchao.insomnia.api.config.GlobalConfig;

/**
 * 修改用户信息
 *
 * @author cchao
 * @date 2019-02-23
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class EditUserDTO extends UserLoginDTO {
    @Length(max = 24, min = 2, message = "昵称长度2-24字符")
    String nickName;
    String avatar = GlobalConfig.defaultAvatar;
    @Range(min = 1, max = 200, message = "年龄异常")
    int age;
    int gender;
}
