package com.contributor.model.verification;

import com.contributor.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @CreationTimestamp
    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
