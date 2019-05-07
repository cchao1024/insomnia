package me.cchao.insomnia.dao;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import me.cchao.insomnia.constant.enums.WishType;

@Entity
@Data
@DynamicUpdate
public class WishImage extends Auditable {
    @Id
    @GeneratedValue
    private Long id;

    private WishType type;

    private Long contentId;

    private Long userId;

    private String url;
}
