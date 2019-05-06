package me.cchao.insomnia.repository;

import me.cchao.insomnia.dao.Reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cchao
 * @version 2019-03-09
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findByCommentId(long id, Pageable pageable);
}
