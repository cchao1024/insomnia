package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 意见反馈
 */
@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class FeedBack extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;
    String email;
    long userId;
}
