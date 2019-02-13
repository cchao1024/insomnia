package com.cchao.sleep.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class FallMusic {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String src;

    private String singer;

    private int play_count;

    private String cover_img;

    private String add_time;

    private long duration;

    private Date createTime;

    private Date updateTime;
}
