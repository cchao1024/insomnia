package me.cchao.insomnia.api.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import me.cchao.insomnia.common.constant.WishType;
import lombok.Data;

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
