package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.ForumCategory;

import java.util.List;

public interface ForumCategoryDao {
    ForumCategory save(ForumCategory forumCategory);

    List<ForumCategory> findAll();

    ForumCategory findByName(String categoryName);

    ForumCategory findById(Long id);

    boolean existByName(String categoryName);

    void removeByName(String categoryName);
}
