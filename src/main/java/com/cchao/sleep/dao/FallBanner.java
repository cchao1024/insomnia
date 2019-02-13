package com.cchao.sleep.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class FallBanner {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private String name;

    private Integer width;

    private Integer height;

    private int type;

    private Date updateTime;

    private Date createTime;
}
