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
@Table(name = "contributors")
public class Contributor extends UserEntity {

    @Builder.Default
    @Column
    @Enumerated
    private AccountLevel accountLevel = AccountLevel.CONTRIBUTOR;

    @Builder.Default
    @ManyToMany(mappedBy = "contributors")
    private List<Project> projects = new ArrayList<>();
}
