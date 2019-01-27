package com.cchao.sleep.dao;

import javax.persistence.*;

@Entity
@Table(name = "wish")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWish_type() {
        return wish_type;
    }

    public void setWish_type(String wish_type) {
        this.wish_type = wish_type;
    }

    public Integer getWish_content_id() {
        return wish_content_id;
    }

    public void setWish_content_id(Integer wish_content_id) {
        this.wish_content_id = wish_content_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getWished() {
        return wished;
    }

    public void setWished(Integer wished) {
        this.wished = wished;
    }
}
