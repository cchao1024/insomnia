package com.cchao.insomnia.model.javabean.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-02-18
 */
@Data
@Accessors(chain = true)
public class UserBean {
    Long id;
    boolean isVisitor;
    String token;
    String email;
    String nickName;
    String avatar;
    int getLike;
    int gender;
    int age;
}
