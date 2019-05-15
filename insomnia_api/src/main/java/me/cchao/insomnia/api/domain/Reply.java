package me.cchao.insomnia.api.domain;

import org.hibernate.annotations.DynamicUpdate;

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
public class Reply extends Auditable {

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

    public Reply increaseLike() {
        likeCount++;
        return this;
    }
}
