package com.spring.labs.lab6.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@NamedQuery(name = "PostEntity.findById", query = "SELECT '*' FROM PostEntity p WHERE p.id = ?1")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String content;
    @Column(length = 1000)
    private String description;
    @Min(0)
    @Column(nullable = false)
    private int upVotes;
    @Min(0)
    @Column(nullable = false)
    private int downVotes;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne(cascade = {CascadeType.ALL})
    private UserEntity author;
    @ManyToOne
    private TopicEntity topic;
}
