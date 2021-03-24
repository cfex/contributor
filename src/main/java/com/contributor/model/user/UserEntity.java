package com.contributor.model.user;

import com.contributor.model.Authority;
import com.contributor.model.Comment;
import com.contributor.model.enumeration.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE users SET active = 0 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active <> 'INACTIVE'")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserEntity implements Comparable<UserEntity> {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, insertable = false)
    private int version;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth_date", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past
    private LocalDate birthDate;

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

    @Column
    @Enumerated
    private AccountStatus active = AccountStatus.ACTIVE;

    //Disabling setter for authorities, because we want to send only one parameter.
    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

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
}
