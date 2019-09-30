package me.cchao.insomnia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.cchao.insomnia.api.domain.FeedBack;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
}