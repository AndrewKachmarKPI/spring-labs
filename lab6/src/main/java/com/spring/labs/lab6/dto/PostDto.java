package com.spring.labs.lab6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostDto {

    private Long id;
    private String name;
    private String description;
    private String content;
    private LocalDateTime creationDate;
    private UserDto author;
    private TopicDto topic;
    private int upVotes;
    private int downVotes;
}
