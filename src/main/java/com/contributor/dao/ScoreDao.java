package com.contributor.dao;

import com.contributor.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreDao extends JpaRepository<Score, Long> {
    Optional<Score> findByUserId(Long id);
}
