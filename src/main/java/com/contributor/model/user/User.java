package com.contributor.model.user;

import com.contributor.model.Project;
import com.contributor.model.ProjectContributors;
import com.contributor.model.enumeration.AccountLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
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

    @Builder.Default
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private List<Project> hosted = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectContributors> projects = new ArrayList<>();
}
