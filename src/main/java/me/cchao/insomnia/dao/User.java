package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;
    private String avatar;

    private String password;
    private int gender;
    private int visitor;
    private int age;
    private int getLike;
    private Date updateTime;
    private Date createTime;

    public User increaseLike() {
        getLike++;
        return this;
    }
}
