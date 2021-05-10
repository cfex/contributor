package com.contributor.service;

import com.contributor.payload.request.CommentRequest;
import com.contributor.security.AppUserDetailsModel;

public interface CommentService {
    void createComment(AppUserDetailsModel author, String projectId, CommentRequest commentRequest);
}
