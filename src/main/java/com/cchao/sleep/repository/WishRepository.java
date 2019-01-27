package com.cchao.sleep.repository;

import com.cchao.sleep.dao.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {


}