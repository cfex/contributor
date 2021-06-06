package com.contributor.service.impl;

import com.contributor.config.ApplicationUtils;
import com.contributor.dao.ProjectContributorDao;
import com.contributor.dao.ProjectDao;
import com.contributor.dao.UserDao;
import com.contributor.dao.VoteDao;
import com.contributor.exception.errors.ProjectAlreadyExistsException;
import com.contributor.exception.errors.UserNotFoundException;
import com.contributor.model.Project;
import com.contributor.model.ProjectContributors;
import com.contributor.model.ProjectContributorsPK;
import com.contributor.model.enumeration.ScoreValue;
import com.contributor.model.user.User;
import com.contributor.payload.request.ProjectUpdateRequest;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.ProjectService;
import com.contributor.service.ScoreService;
import com.contributor.shared.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserDao userDao;
    private final VoteDao voteDao;
    private final ProjectDao projectDao;
    private final ApplicationUtils utils;
    private final ModelMapper modelMapper;
    private final ScoreService scoreService;
    private final ProjectContributorDao projectContributorDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseMinified> fetchAll() {
        LOGGER.info("[Fetching project on thread {}] [Triggered on {}]",
                Thread.currentThread().getName(), LocalDateTime.now());

        return projectDao.findAllByOrderByCreatedAtDesc().stream()
                .filter(Project::getIsPublished)
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseMinified> findAllBySearchQuery(String keyword) {
        LOGGER.info("[Fetching project on thread {}] [Triggered on {}] [For query {}]",
                Thread.currentThread().getName(), LocalDateTime.now(), keyword);

        return projectDao.findAllByKeyword(keyword).stream()
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseMinified> popularProjectsThisWeek() {
        return projectDao.popularThisWeek().stream()
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse findByProjectId(String projectId) {
        LOGGER.info("[Fetching project on thread {}] [Triggered on {}] [For query {}]",
                Thread.currentThread().getName(), LocalDateTime.now(), projectId);

        Project byProjectId = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow();
        return modelMapper.map(byProjectId, ProjectResponse.class);
    }

    @Override
    @Transactional
    public void updateProject(ProjectUpdateRequest updateRequest, String projectId) {
        Project project = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow(RuntimeException::new);
        project.setTitle(updateRequest.getTitle());
        project.setDescription(updateRequest.getDescription());
        project.setIntro(updateRequest.getIntro());
        project.setGithub_url(updateRequest.getGithub_url());
        project.setDevelopmentStatus(updateRequest.getDevStatus());
        project.setIsPublished(updateRequest.getIsPublished() != null);

        projectDao.save(project);
    }

    @SneakyThrows
    @Override
    @Async
    public CompletableFuture<ProjectResponse> createProject(AppUserDetailsModel host, ProjectDto projectDto) {
        LOGGER.info("[Creating project on thread {}] [Triggered on {}] [By {}]",
                Thread.currentThread().getName(), LocalDateTime.now(), host.getUsername());

        User user = userDao.findByUsernameIgnoreCase(host.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Project projectMap = modelMapper.map(projectDto, Project.class);
        if (projectDao.findByTitle(projectDto.getTitle()).isEmpty()) {
            LOGGER.info("PROJECT WITH SAME NAME NOT FOUND!");
            projectMap.setHost(user);
            projectMap.setProjectId(utils.generateUUID());
            scoreService.updateScore(user, ScoreValue.PROJECT_HOST_VALUE);
        } else {
            LOGGER.info("Project with same name exists. Error thrown!");
            throw new ProjectAlreadyExistsException("error creating project");
        }

        return CompletableFuture.supplyAsync(() -> modelMapper.map(projectDao.save(projectMap), ProjectResponse.class));
    }

    @Override
    @Transactional
    @Async
    public void requestContribution(AppUserDetailsModel auth, String projectId) {
        LOGGER.info("[Processing event on thread {}] [Triggered on {}] [For project {}, requested by {}]",
                Thread.currentThread().getName(), LocalDateTime.now(), projectId, auth.getUsername());

        Project project = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow();
        User contributor = userDao.findByUsername(auth.getUsername()).orElseThrow();

        ProjectContributorsPK buildPK = ProjectContributorsPK.builder()
                .project_id(project.getId())
                .user_id(contributor.getId())
                .build();

        if (projectContributorDao.findById(buildPK).isEmpty()) {
            ProjectContributors build = ProjectContributors.builder()
                    .id(buildPK)
                    .project(project)
                    .contributor(contributor)
                    .build();
            projectContributorDao.save(build);
            scoreService.updateScore(contributor, ScoreValue.PROJECT_CONTRIBUTION_VALUE);
        }
    }

    @Override
    @Transactional
    public void delete(String projectId) {
        Optional<Project> project =  projectDao.findByProjectIdOrderByCreatedAtDesc(projectId);
        if(project.isPresent()) {
            voteDao.deleteByProjectId(project.get().getId());
            projectDao.deleteByProjectId(projectId);
        }
    }
}
