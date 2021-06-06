package com.contributor.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ProjectContributorsPK implements Serializable {

    @Column(name = "project_id")
    private Long project_id;

    @Column(name = "user_id")
    private Long user_id;
}
