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
 * 二级评论
 *
 * @author : cchao
 * @version 2019-03-09
 */
@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long postId;
    long commentId;
    long commentUserId;

    long replyUserId;

    String content;
    String images;

    int likeCount;
    Date createTime;
    Date updateTime;

    public Reply increaseLike() {
        likeCount++;
        return this;
    }
}
