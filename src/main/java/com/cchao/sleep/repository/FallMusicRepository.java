package com.cchao.sleep.repository;

import com.cchao.sleep.dao.FallMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FallMusicRepository extends JpaRepository<FallMusic, Long> {

    boolean existsByName(String name);
}