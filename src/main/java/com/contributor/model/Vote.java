package com.contributor.model;

import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @CreationTimestamp
    @Column(name = "voted_on")
    private LocalDateTime votedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
}

