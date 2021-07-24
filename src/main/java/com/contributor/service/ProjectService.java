package com.contributor.service;

import com.contributor.exception.ProjectAlreadyExistsException;
import com.contributor.payload.request.ProjectUpdateRequest;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.shared.ProjectDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProjectService {
    List<ProjectResponseMinified> fetchAll();

    List<ProjectResponseMinified> findAllBySearchQuery(String keyword);

    List<ProjectResponseMinified> popularProjectsThisWeek();

    ProjectResponse findByProjectId(String projectId);

    void updateProject(ProjectUpdateRequest updateRequest, String projectId);
    
    CompletableFuture<ProjectResponse> createProject(AppUserDetailsModel host, ProjectDto projectDto);

    void requestContribution(AppUserDetailsModel auth, String projectId);

    void delete(String projectId);
}
