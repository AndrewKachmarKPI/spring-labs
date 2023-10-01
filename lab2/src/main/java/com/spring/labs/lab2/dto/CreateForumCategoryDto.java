package com.spring.labs.lab2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateForumCategoryDto {
    private String categoryName;
    private String description;
    private String username;
}
