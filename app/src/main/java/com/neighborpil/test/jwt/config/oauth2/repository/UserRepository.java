package com.neighborpil.test.jwt.config.oauth2.repository;

import com.neighborpil.test.jwt.user.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
