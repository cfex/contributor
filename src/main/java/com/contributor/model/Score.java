package com.contributor.model;

import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "value", nullable = false)
    private int value = 0;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private LocalDate updatedOn;
}
