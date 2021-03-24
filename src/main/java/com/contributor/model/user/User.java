package com.contributor.model.user;

import com.contributor.model.Project;
import com.contributor.model.enumeration.AccountLevel;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends UserEntity {

    @Builder.Default
    private AccountLevel accountLevel = AccountLevel.VISITOR;

    @Builder.Default
    @OneToMany(mappedBy = "host")
    private List<Project> hosted = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "contributor_projects",
            joinColumns = @JoinColumn(name = "contributor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> projects = new ArrayList<>();
}
