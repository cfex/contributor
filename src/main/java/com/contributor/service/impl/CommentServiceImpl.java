package com.contributor.service.impl;

import com.contributor.dao.CommentDao;
import com.contributor.dao.ProjectDao;
import com.contributor.dao.UserDao;
import com.contributor.exception.UserNotFoundException;
import com.contributor.model.Comment;
import com.contributor.model.Project;
import com.contributor.model.user.User;
import com.contributor.payload.request.CommentRequest;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.CommentService;
import com.contributor.shared.CommentDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserDao userDao;
    private final ProjectDao projectDao;
    private final CommentDao commentDao;
    private final ModelMapper modelMapper;

    @SneakyThrows
    @Override
    @Transactional
    public void createComment(@AuthenticationPrincipal AppUserDetailsModel author, String projectId, CommentRequest commentRequest) {
        User user = userDao.findByUsernameIgnoreCase(author.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Project project = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow(
                () -> new RuntimeException("No project found"));

        CommentDto commentMap = modelMapper.map(commentRequest, CommentDto.class);
        Comment comment = modelMapper.map(commentMap, Comment.class);
        comment.setAuthor(user);
        comment.setProject(project);
        commentDao.saveAndFlush(comment);
    }
}
