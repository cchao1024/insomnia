package me.cchao.insomnia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import me.cchao.insomnia.dao.FeedBack;
import me.cchao.insomnia.dao.WishImage;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
}