package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一级评论
 *
 * @author : cchao
 * @version 2019-03-09
 */
@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long postId;
    long postUserId;
    long commentUserId;

    String content;
    String images;

    int likeCount;
    int reviewCount;

    public Comment increaseLike() {
        likeCount++;
        return this;
    }

    public Comment increaseReview() {
        reviewCount++;
        return this;
    }
}
