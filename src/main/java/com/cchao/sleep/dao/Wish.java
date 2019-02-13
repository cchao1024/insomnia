package com.cchao.sleep.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wish")
@Data
public class Wish {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String wish_type;

    @Column(nullable = false)
    private Integer wish_content_id;

    @Column(nullable = false)
    private Integer user_id;

    @Column(nullable = false)
    private Integer wished;

    public Wish(String wish_type, Integer wish_content_id, Integer user_id) {
        this.wish_type = wish_type;
        this.wish_content_id = wish_content_id;
        this.user_id = user_id;
    }
}
