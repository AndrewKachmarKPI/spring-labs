package com.spring.labs.lab5.dao.fake;

import com.spring.labs.lab5.dao.ForumCategoryDao;
import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class FakeForumCategoryDao implements ForumCategoryDao {
    private final Map<String, ForumCategory> categories = new HashMap<>();

    @Override
    public ForumCategory save(ForumCategory forumCategory) {
        if (Optional.ofNullable(forumCategory.getId()).isEmpty()) {
            forumCategory = forumCategory.toBuilder().id((long) categories.size() + 1).build();
        } else {
            ForumCategory finalForumCategory = forumCategory;
            ForumCategory category = findById(forumCategory.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category is with " + finalForumCategory.getId() + " is not found"));
            categories.remove(category.getCategoryName());
        }
        return categories.put(forumCategory.getCategoryName(), forumCategory);
    }

    @Override
    public List<ForumCategory> findAll() {
        return categories.values().stream().sorted(Comparator.comparing(ForumCategory::getCreated).reversed()).toList();
    }

    @Override
    public int totalSize() {
        return categories.size();
    }

    @Override
    public List<ForumCategory> findAllPageableAndFiltered(Integer page, Integer pageSize, String title) {
        List<ForumCategory> categoryList = findAll();
        if (!isNull(title)) {
            categoryList = categoryList.stream()
                    .filter(forumCategory -> forumCategory.getCategoryName().startsWith(title))
                    .toList();
        }
        if (!isNull(page) && !isNull(pageSize)) {
            categoryList = categoryList.stream()
                    .skip((long) (page - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }
        return categoryList;
    }

    @Override
    public Optional<ForumCategory> findByName(String categoryName) {
        if (!existByName(categoryName)) {
            throw new ResourceNotFoundException("Category with name " + categoryName + " is not found");
        }
        return Optional.of(categories.get(categoryName));
    }

    @Override
    public Optional<ForumCategory> findById(Long id) {
        return categories.values().stream()
                .filter(category -> category.getId().equals(id)).findAny();
    }

    @Override
    public boolean existByName(String categoryName) {
        return categories.containsKey(categoryName);
    }

    @Override
    public void removeByName(String categoryName) {
        if (!existByName(categoryName)) {
            throw new ResourceNotFoundException("Category with name " + categoryName + " is not found");
        }
        categories.remove(categoryName);
    }
}
