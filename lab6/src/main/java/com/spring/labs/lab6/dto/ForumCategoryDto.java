package com.spring.labs.lab6.dto;

import com.spring.labs.lab6.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public final class ForumCategoryDto {
    private final Long id;
    private final LocalDateTime created;
    private final String categoryName;
    private final String description;
    private final String backgroundImage;
    private final UserDto moderator;
}
