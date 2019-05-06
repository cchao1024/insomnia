package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    long userId;
    int likeCount;
    int reviewCount;
    String content;
    String images;
    String tags;
    Date createTime;
    Date updateTime;

    public Post increaseLike() {
        likeCount++;
        return this;
    }
    public Post increaseReview() {
        reviewCount++;
        return this;
    }
}
