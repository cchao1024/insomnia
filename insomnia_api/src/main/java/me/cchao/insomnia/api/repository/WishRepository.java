package me.cchao.insomnia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import me.cchao.insomnia.api.domain.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findByUserId(Long userId, Pageable pageable);
}