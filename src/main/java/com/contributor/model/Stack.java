package com.contributor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stack")
public class Stack implements Comparable<Stack>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, insertable = false)
    private int version;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "creator")
    private String creator;

    @Past
    @Column(name = "published_date")
    private LocalDateTime publishDate;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date", insertable = false)
    private LocalDateTime updateDate;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "project_stack",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stack_id", referencedColumnName = "id"))
    private List<Project> projects = new ArrayList<>();

    @Override
    public int compareTo(Stack o) {
        return this.id.compareTo(o.id);
    }
}
