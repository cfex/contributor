package com.contributor.model;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
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

    @Column(nullable = false, unique = true)
    private String projectId;

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

    @Builder.Default
    @Column(name = "dev_status")
    @Enumerated
    private DevStatus developmentStatus = DevStatus.OPEN;

    @Builder.Default
    @Column(name = "published")
    private Boolean published = false;

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
    private User host;

    @Builder.Default
    @ManyToMany(mappedBy = "projects")
    private List<User> contributors = new ArrayList<>();

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
