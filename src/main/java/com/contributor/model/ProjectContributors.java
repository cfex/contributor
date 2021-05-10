package com.contributor.model;

import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project_contributors")
public class ProjectContributors implements Serializable {

    @EmbeddedId
    private ProjectContributorsPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User contributor;

    @Column(name = "joined", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime joined;

    @Builder.Default
    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;
}
