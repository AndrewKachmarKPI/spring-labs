package com.spring.labs.lab6.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@NamedQuery(name = "ForumCategoryEntity.findCategoryById",query = "SELECT '*' FROM ForumCategoryEntity c WHERE c.id = ?1")
public class ForumCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private String categoryName;
    @Column(length = 1000)
    private String description;
    @Column
    private String backgroundImage;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserEntity moderator;
}
