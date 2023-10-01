package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.ForumCategory;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class FakeForumCategoryDao implements ForumCategoryDao {
    private final Map<String, ForumCategory> categories = new HashMap<>();

    @Override
    public ForumCategory save(ForumCategory forumCategory) {
        forumCategory = forumCategory.toBuilder().id((long) categories.size() + 1).build();
        return categories.put(forumCategory.getCategoryName(), forumCategory);
    }

    @Override
    public List<ForumCategory> findAll() {
        return categories.values().stream().sorted(Comparator.comparing(ForumCategory::getCreated).reversed()).toList();
    }

    @Override
    public ForumCategory findByName(String categoryName) {
        if (!existByName(categoryName)) {
            throw new RuntimeException("Category is not found");
        }
        return categories.get(categoryName);
    }

    @Override
    public boolean existByName(String categoryName) {
        return categories.containsKey(categoryName);
    }

    @Override
    public void removeByName(String categoryName) {
        if (!existByName(categoryName)) {
            throw new RuntimeException("Category is not found");
        }
        categories.remove(categoryName);
    }
}
