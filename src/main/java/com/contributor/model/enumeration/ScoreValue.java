package com.contributor.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScoreValue {
    PROJECT_HOST_VALUE(50),
    PROJECT_CONTRIBUTION_VALUE(30),
    PROJECT_VOTE(10);

    private final int value;
}
