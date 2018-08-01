package com.example.demo2.dao;

import com.example.demo2.entity.FallImage;
import com.example.demo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FallImageRepository extends JpaRepository<FallImage, Long> {


}