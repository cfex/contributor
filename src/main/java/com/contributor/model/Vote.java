package com.contributor.model;

import com.contributor.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, insertable = false)
    private int version;

    @CreationTimestamp
    @Column(name = "voted_on")
    private LocalDateTime votedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
}
