package com.spring.labs.lab2.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime lastUpdatedDate;
    private int views;
    private List<String> tags;
    private int upVotes;
    private int downVotes;
    private boolean locked;
    private ForumCategory forumCategory;
}
