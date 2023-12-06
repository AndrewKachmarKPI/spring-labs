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
@NamedQuery(name = "TopicEntity.findById", query = "select '*' from TopicEntity t where t.id=?1")
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String content;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private UserEntity author;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    private ForumCategoryEntity forumCategory;
}
