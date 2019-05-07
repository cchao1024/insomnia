package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.config.GlobalConfig;

@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class User extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;
    private String avatar = GlobalConfig.defaultAvatar;

    private String password;
    private int gender;
    private int visitor;
    private int age;
    private int getLike;

    public User increaseLike() {
        getLike++;
        return this;
    }
}
