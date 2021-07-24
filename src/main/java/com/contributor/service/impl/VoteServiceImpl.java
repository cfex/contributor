package com.contributor.service.impl;

import com.contributor.dao.ProjectDao;
import com.contributor.dao.UserDao;
import com.contributor.dao.VoteDao;
import com.contributor.exception.UserNotFoundException;
import com.contributor.model.Project;
import com.contributor.model.Vote;
import com.contributor.model.enumeration.ScoreValue;
import com.contributor.model.user.User;
import com.contributor.service.ScoreService;
import com.contributor.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteDao voteDao;
    private final UserDao userDao;
    private final ProjectDao projectDao;
    private final ScoreService scoreService;

    @SneakyThrows
    @Override
    @Transactional
    public void vote(String projectId, String username) {
        User voter = userDao.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Project project = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow(RuntimeException::new);

        if (!userAlreadyVoted(voter.getId(), project.getId())) {
            scoreService.updateScore(voter, ScoreValue.PROJECT_VOTE);
            voteDao.save(
                    Vote.builder()
                            .voter(voter)
                            .project(project)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public Integer voteCount(String projectId) {
        return voteDao.countAllByProjectProjectId(projectId);
    }

    @Override
    public Boolean userAlreadyVoted(Long userId, Long postId) {
        return voteDao.findByVoterIdAndProjectId(userId, postId).isPresent();
    }
}
