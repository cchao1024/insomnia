package me.cchao.insomnia.repository;

import me.cchao.insomnia.dao.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cchao
 * @version 2019-03-09
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(long postId, Pageable pageable);
}
