package com.contributor.service;

import com.contributor.model.enumeration.ScoreValue;
import com.contributor.model.user.User;

public interface ScoreService {
    void updateScore(User user, ScoreValue value);
}
