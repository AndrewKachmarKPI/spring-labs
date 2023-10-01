package com.spring.labs.lab2.dao;

import com.github.javafaker.Faker;
import com.spring.labs.lab2.domain.ForumCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class FakeForumCategoryDao implements ForumCategoryDao {
    private final Faker faker;
    private Map<String, ForumCategory> categories = new HashMap<>();

    @Override
    public ForumCategory save(ForumCategory forumCategory) {
        forumCategory = forumCategory.toBuilder().id((long) categories.size() + 1).build();
        return categories.put(forumCategory.getCategoryName(), forumCategory);
    }

    @Override
    public List<ForumCategory> findAll() {
        return categories.values().stream().toList();
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

    @Override
    public void generateDefaultCategories(Integer size) {
        List<String> categoryNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size).toList();
        categories = IntStream.range(1, size).mapToObj(index -> ForumCategory.builder()
                .id((long) index)
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .build()).collect(Collectors.toMap(ForumCategory::getCategoryName, category -> category));
    }
}
