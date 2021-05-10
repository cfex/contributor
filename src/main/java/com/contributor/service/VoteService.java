package com.contributor.service;

import org.springframework.stereotype.Service;

public interface VoteService {
    void vote(String projectId, String username);

    Integer voteCount(String projectId);

    Boolean userAlreadyVoted(Long userId, Long projectId);
}