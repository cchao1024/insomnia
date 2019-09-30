package me.cchao.insomnia.api.repository;

import me.cchao.insomnia.api.domain.FallMusic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FallMusicRepository extends JpaRepository<FallMusic, Long> {

    boolean existsByName(String name);
}