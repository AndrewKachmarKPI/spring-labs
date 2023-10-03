package com.spring.labs.lab4.dao;


import com.spring.labs.lab4.domain.ForumCategory;

import java.util.List;
import java.util.Optional;

public interface ForumCategoryDao {
    ForumCategory save(ForumCategory forumCategory);

    List<ForumCategory> findAll();

    int totalSize();

    List<ForumCategory> findAllPageableAndFiltered(Integer offset, Integer limit, String title);

    Optional<ForumCategory> findByName(String categoryName);

    Optional<ForumCategory> findById(Long id);

    boolean existByName(String categoryName);

    void removeByName(String categoryName);
}
