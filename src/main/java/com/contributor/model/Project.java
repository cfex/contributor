package com.contributor.model;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Comparable<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String projectId;

    @NaturalId(mutable = true)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "intro", length = 500)
    private String intro;

    @Lob
    @Column(name = "description", nullable = false, length = 5000)
    private String description;

    @Builder.Default
    @Column(name = "dev_status")
    @Enumerated(EnumType.STRING)
    private DevStatus developmentStatus = DevStatus.OPEN;

    @Builder.Default
    @Column(name = "published")
    private Boolean isPublished = false;

    @Column(name = "github_url")
    private String github_url;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User host;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectContributors> contributors = new ArrayList<>();

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    @Override
    public int compareTo(Project o) {
        return title.compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;

        return title != null && title.equals(project.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
