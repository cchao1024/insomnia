package me.cchao.insomnia.api.repository;

import me.cchao.insomnia.api.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cchao
 * @version 2019-03-09
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
