package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.ForumCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FakeForumCategoryDao implements ForumCategoryDao {
	private final Map<String, ForumCategory> categories = new HashMap<>();

	@Override
	public ForumCategory save(ForumCategory forumCategory) {
		if (Optional.ofNullable(forumCategory.getId()).isEmpty()) {
			forumCategory = forumCategory.toBuilder().id((long) categories.size() + 1).build();
		} else {
			ForumCategory category = findById(forumCategory.getId());
			categories.remove(category.getCategoryName());
		}
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
	public ForumCategory findById(Long id) {
		return categories.values().stream().filter(category -> category.getId().equals(id)).findAny()
				.orElseThrow(() -> new RuntimeException("Category with id:" + id + " is not found"));
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
