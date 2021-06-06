package com.contributor.dao;

import com.contributor.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUserIdOrUsername(String userId, String username);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmailIgnoreCase(String username, String email);
}
