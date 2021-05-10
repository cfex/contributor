package com.contributor.service.impl;

import com.contributor.config.ApplicationUtils;
import com.contributor.dao.ProjectContributorDao;
import com.contributor.dao.ProjectDao;
import com.contributor.dao.UserDao;
import com.contributor.model.Project;
import com.contributor.model.ProjectContributors;
import com.contributor.model.user.User;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.ProjectService;
import com.contributor.shared.ProjectDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserDao userDao;
    private final ProjectDao projectDao;
    private final ApplicationUtils utils;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<ProjectResponseMinified> fetchAll() {
        LOGGER.info("Fetching projects on thread -> {}", Thread.currentThread().getName());
        return projectDao.findAllByOrderByCreatedAtDesc().stream()
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> findAllBySearchQuery(String keyword) {
        return projectDao.findAllByKeyword(keyword);
    }

    @Override
    public List<ProjectResponseMinified> popularProjectsThisWeek() {
        return projectDao.popularThisWeek().stream()
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse findByProjectId(String projectId) {
        Project byProjectId = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow();
        return modelMapper.map(byProjectId, ProjectResponse.class);
    }

    //TODO async
    @Override
//    @Async
    public ProjectResponse createProject(AppUserDetailsModel host, ProjectDto projectDto) {
        User user = userDao.findByUsernameIgnoreCase(host.getUsername()).orElseThrow();

        Project projectMap = modelMapper.map(projectDto, Project.class);
        projectMap.setHost(user);
        projectMap.setProjectId(utils.generateUUID());
        return modelMapper.map(projectDao.save(projectMap), ProjectResponse.class);
    }
}
