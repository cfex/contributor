package com.contributor.dao;

import com.contributor.model.ProjectContributors;
import com.contributor.model.ProjectContributorsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectContributorDao extends JpaRepository<ProjectContributors, ProjectContributorsPK> {
}
