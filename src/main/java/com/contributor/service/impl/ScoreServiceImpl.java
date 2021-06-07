package com.contributor.service.impl;

import com.contributor.dao.ScoreDao;
import com.contributor.model.Score;
import com.contributor.model.enumeration.ScoreValue;
import com.contributor.model.user.User;
import com.contributor.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreDao scoreDao;

    @Override
    @Transactional
    public void updateScore(User user, ScoreValue value) {
        Optional<Score> score = scoreDao.findByUserId(user.getId());
        if (score.isPresent()) {
            Score scoreObj = score.get();
            int currentValue = scoreObj.getValue();
            scoreObj.setValue(currentValue + value.getValue());
        } else {
            scoreDao.save(Score.builder()
                    .user(user)
                    .value(value.getValue())
                    .build()
            );
        }
    }
}
