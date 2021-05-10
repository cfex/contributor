package com.contributor.model;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@EqualsAndHashCode
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

    @NaturalId
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "intro", nullable = false)
    private String intro;

    @Lob
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Builder.Default
    @Column(name = "dev_status")
    @Enumerated(EnumType.STRING)
    private DevStatus developmentStatus = DevStatus.OPEN;

    @Builder.Default
    @Column(name = "published")
    private Boolean published = false;

    @Column(name = "github_url")
    private String github_url;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User host;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectContributors> contributors = new ArrayList<>();

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addContributor(ProjectContributors user) {
        this.contributors.add(user);
    }

    @Override
    public int compareTo(Project o) {
        return title.compareTo(o.getTitle());
    }
}
