package com.contributor.dao;

import com.contributor.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByCreatedAtDesc();

    Optional<Project> findByTitle(String title);

    Optional<Project> findByProjectIdOrderByCreatedAtDesc(String projectId);

    void deleteByProjectId(String projectId);

    /**
     * Select top 3 projects with the most likes, that are created in the last 7 days.
     *
     * @return list of projects
     */
    @Query(value = "SELECT * FROM PROJECTS INNER JOIN VOTES ON PROJECTS.ID = VOTES.PROJECT_ID  " +
            "WHERE PROJECTS.CREATED_AT >= DATE_ADD(CURDATE(), INTERVAL -7 DAY)" +
            "GROUP BY VOTES.PROJECT_ID ORDER BY COUNT(*) DESC LIMIT 3",
            nativeQuery = true)
    List<Project> popularThisWeek();

    @Query(value = "SELECT * FROM PROJECTS INNER JOIN VOTES ON PROJECTS.ID = VOTES.PROJECT_ID  " +
            "GROUP BY VOTES.PROJECT_ID ORDER BY COUNT(*) DESC LIMIT 3",
            nativeQuery = true)
    List<Project> popularAllTime();

    @Query(value = "SELECT p FROM Project p WHERE p.title LIKE %?1% " +
            "OR p.projectId LIKE %?1% " +
            "OR p.host.username LIKE %?1%")
    List<Project> findAllByKeyword(String keyword);
}
