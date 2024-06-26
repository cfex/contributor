package com.contributor.model.user;

import com.contributor.model.Authority;
import com.contributor.model.enumeration.AccountStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE users SET active = 0 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active <> 'INACTIVE'")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserEntity implements Serializable, Comparable<UserEntity> {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Lob
    @Column(name = "avatar", length = 500)
    private String avatar;

    @Column(name = "birth_date", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Lob
    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "school")
    private String school;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder.Default
    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus active = AccountStatus.ACTIVE;

    @Column
    private String emailVerificationToken;

    @Builder.Default
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    //Disabling setter for authorities, because we want to send only one parameter.
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<>();

    /**
     * Used for setting authority for UserEntity
     *
     * @param authority Object
     */
    public void setAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    //Setting activity status on ActivityStatus.DELETED, before hibernate takes action
    @PreRemove
    private void onUserDelete() {
        this.active = AccountStatus.INACTIVE;
    }

    @Override
    public int compareTo(UserEntity o) {
        return this.id.compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result=17;
        result=31*result+18;
        result=31*result+(name!=null ? name.hashCode():0);
        return result;
    }

}
