package com.example.demo2.dao;

import com.example.demo2.entity.FallMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FallMusicRepository extends JpaRepository<FallMusic, Long> {

    boolean existsByName(String name);
}