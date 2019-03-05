package com.cchao.insomnia.repository;

import com.cchao.insomnia.dao.FallMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FallMusicRepository extends JpaRepository<FallMusic, Long> {

    boolean existsByName(String name);
}