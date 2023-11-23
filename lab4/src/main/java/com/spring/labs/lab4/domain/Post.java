package com.spring.labs.lab4.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Post {

    private Long id;
    private User author;
    private Topic topic;
    private String name;
    private String content;
    private String description;
    private LocalDateTime creationDate;
    private int upVotes;
    private int downVotes;
}
