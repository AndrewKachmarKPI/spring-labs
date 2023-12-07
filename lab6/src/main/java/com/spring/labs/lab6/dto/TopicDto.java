package com.spring.labs.lab6.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class TopicDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
    private LocalDateTime creationDate;
    private ForumCategoryDto forumCategory;
}
