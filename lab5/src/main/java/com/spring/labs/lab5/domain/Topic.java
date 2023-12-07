package com.spring.labs.lab5.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Topic {
    private Long id;
    private String title;
    private String content;
    private User author;
    private LocalDateTime creationDate;
    private ForumCategory forumCategory;
}
