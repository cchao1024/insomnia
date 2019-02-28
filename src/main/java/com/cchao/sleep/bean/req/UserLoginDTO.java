package com.cchao.sleep.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * 用户登录
 *
 * @author Jcxcc
 * @date 2019-02-23
 * @since 1.0
 */
@Data
@ApiModel
public class UserLoginDTO {

    @ApiModelProperty("电子邮箱")
    @Email(message = "不是有效的电子邮箱")
    private String email;

    @ApiModelProperty("密码")
    @Length(max = 12, min = 6, message = "密码需为6到12位英文和数字组合")
    private String password;
}
