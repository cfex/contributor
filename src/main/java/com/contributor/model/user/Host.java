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
@Table(name = "hosts")
public class Host extends UserEntity {

    @Builder.Default
    @Column
    @Enumerated
    private AccountLevel accountLevel = AccountLevel.HOST;

    @Builder.Default
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();
}
