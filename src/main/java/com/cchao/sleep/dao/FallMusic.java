package com.cchao.sleep.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Fall_Music")
@Data
public class FallMusic {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String src;

    @Column(nullable = false)
    private String singer;

    @Column(nullable = false)
    private int play_count;

    @Column(nullable = false)
    private String cover_img;

    @Column(nullable = false)
    private String add_time;

    @Column(nullable = false)
    private long duration;

}
