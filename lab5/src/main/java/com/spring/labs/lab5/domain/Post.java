package com.spring.labs.lab5.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Post {

    private Long id;
    private String name;
    private String content;
    private String description;
    private LocalDateTime creationDate;
    private int upVotes;
    private int downVotes;
    private User author;
    private Topic topic;
}
