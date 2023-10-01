package com.spring.labs.lab2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public final class ForumCategory {
    private final Long id;
    private final LocalDateTime created;
    private final String categoryName;
    private final String description;
    private final String backgroundImage;
    private final User moderator;
}
