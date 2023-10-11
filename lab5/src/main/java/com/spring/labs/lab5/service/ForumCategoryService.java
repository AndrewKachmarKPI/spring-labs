package com.spring.labs.lab5.service;

import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.dto.CreateForumCategoryDto;
import com.spring.labs.lab5.dto.PageDto;
import net.datafaker.Faker;

import java.util.List;

public interface ForumCategoryService {
    ForumCategory create(CreateForumCategoryDto forumCategory);

    ForumCategory update(Long categoryId, CreateForumCategoryDto forumCategory);

    PageDto<ForumCategory> findAll(Integer pageNumber, Integer pageSize, String title);
    List<ForumCategory> findAll();

    void deleteByName(String categoryName);

    ForumCategory findByName(String categoryName);

    ForumCategory findById(Long id);

    void generateDefaultCategories(Integer size, Faker faker);
}
