package com.contributor.model;

import com.contributor.model.enumeration.Authorities;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "authority")
    private Authorities authority;
}
