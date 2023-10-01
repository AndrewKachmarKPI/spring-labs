package com.spring.labs.lab2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public final class ForumCategory {
    private final Long id;
    private final String categoryName;
    private final String description;
    private final User moderator;
}
