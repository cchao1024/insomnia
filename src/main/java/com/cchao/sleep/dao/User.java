package com.cchao.sleep.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String role;

    private Integer age;

    private String updateTime;

    private String createTime;
}
