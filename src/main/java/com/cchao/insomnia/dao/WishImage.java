package com.cchao.insomnia.dao;

import com.cchao.insomnia.constant.enums.WishType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
public class WishImage {
    @Id
    @GeneratedValue
    private Long id;

    private WishType type;

    private Long contentId;

    private Long userId;

    private String url;

    private String updateTime;

    private String createTime;
}
