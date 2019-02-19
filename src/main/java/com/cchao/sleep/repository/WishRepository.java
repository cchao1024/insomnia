package com.cchao.sleep.repository;

import com.cchao.sleep.dao.WishImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<WishImage, Long> {

    Page<WishImage> findByUserId(Long userId, Pageable pageable);

}