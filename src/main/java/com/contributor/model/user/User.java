package com.contributor.model.user;

import com.contributor.model.Project;
import com.contributor.model.ProjectContributors;
import com.contributor.model.Score;
import com.contributor.model.Vote;
import com.contributor.model.enumeration.AccountLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends UserEntity {

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AccountLevel accountLevel = AccountLevel.BASIC;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score;

    @Builder.Default
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private List<Project> hosted = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectContributors> projects = new ArrayList<>();
}
