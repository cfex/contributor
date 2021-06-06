package com.contributor.config;

import com.contributor.dao.ProjectDao;
import com.contributor.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthValidation {

    private final ProjectDao projectDao;

    /**
     * It takes the project id, finds it in the database,
     * and search if that project belongs to the currently logged user.
     * If not, the request will be denied.
     *
     * @param projectId the id over which the check was requested
     * @param authenticationUsername the value of currently logged user
     * @return true if request is allowed, else false.
     */
    public boolean checkByID(String projectId, String authenticationUsername) {
        Project project = projectDao.findByProjectIdOrderByCreatedAtDesc(projectId).orElseThrow();
        return project.getHost().getUsername().equals(authenticationUsername);
    }
}
