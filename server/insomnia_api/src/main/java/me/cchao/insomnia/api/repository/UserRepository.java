package me.cchao.insomnia.api.repository;

import me.cchao.insomnia.api.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by email user.
     *
     * @param name the name
     * @return the user
     */
    Optional<User> findByEmail(String name);
}