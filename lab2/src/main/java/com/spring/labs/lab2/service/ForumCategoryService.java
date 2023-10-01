package com.spring.labs.lab2.service;

import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;

import java.util.List;

public interface ForumCategoryService {
    ForumCategory create(CreateForumCategoryDto forumCategory);

    List<ForumCategory> findAll();

    void deleteByName(String categoryName);

    ForumCategory findByName(String categoryName);

    ForumCategory changeModerator(String categoryName, String username);
}
