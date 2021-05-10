package com.contributor.service;

import com.contributor.model.Project;
import com.contributor.payload.request.CommentRequest;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.shared.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectResponseMinified> fetchAll();

    List<Project> findAllBySearchQuery(String keyword);

    List<ProjectResponseMinified> popularProjectsThisWeek();

    ProjectResponse findByProjectId(String projectId);

    ProjectResponse createProject(AppUserDetailsModel host, ProjectDto projectDto);
}
