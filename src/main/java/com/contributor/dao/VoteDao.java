package com.contributor.dao;

import com.contributor.model.Project;
import com.contributor.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteDao extends JpaRepository<Vote, Long> {
    Integer countAllByProjectProjectId(String projectId);

    void deleteByVoterId(Long id);

    Optional<Vote> findByVoterIdAndProjectId(Long voterId, Long projectId);
}
