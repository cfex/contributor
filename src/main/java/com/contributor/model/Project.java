package com.contributor.model;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.Contributor;
import com.contributor.model.user.Host;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project implements Comparable<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Version
    @Column(name = "version", insertable = false)
    private int version;

    @Column(name = "name", nullable = false, unique = true)
    private String title;

    @Column(name = "intro", nullable = false)
    private String intro;

    @Lob
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "dev_status")
    @Enumerated
    private DevStatus developmentStatus;

    @Column(name = "published")
    private Boolean published;

    @Builder.Default
    @ManyToMany(mappedBy = "projects")
    private List<Stack> stack = new ArrayList<>();

    @Column(name = "github_url")
    private String github_url;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contributor host;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "contributor_projects",
            joinColumns = @JoinColumn(name = "contributor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Contributor> contributors = new ArrayList<>();

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public int compareTo(Project o) {
        return title.compareTo(o.getTitle());
    }
}
