package com.spring.labs.lab6.service;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.dto.ForumCategoryDto;
import com.spring.labs.lab6.dto.create.CreateForumCategoryDto;
import com.spring.labs.lab6.dto.PageDto;
import net.datafaker.Faker;

import java.util.List;

public interface ForumCategoryService {
    ForumCategoryDto create(CreateForumCategoryDto forumCategory);

    ForumCategoryDto update(Long categoryId, CreateForumCategoryDto forumCategory);

    PageDto<ForumCategoryDto> findAll(String moderatorUsername, Integer pageNumber, Integer pageSize);

    List<ForumCategoryDto> findAll();

    void deleteByName(String categoryName);

    ForumCategoryDto findByName(String categoryName);

    ForumCategoryDto findById(Long id);
}
