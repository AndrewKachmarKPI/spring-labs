package com.spring.labs.lab2.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FakePostDao implements PostDao {

	private final Map<String, Post> posts = new HashMap<>();

	@Override
	public void save(Post post) {
		if (post != null && !posts.containsKey(post.getName())) {
			posts.put(post.getName(), post);
		} else {
//          throw new postIsPresentException();
		}
 	}

	@Override
	public List<Post> findAll() {
		return posts.values().stream().toList();
	}

	@Override
	public Post findById(Long id) {
		return posts.values().stream().filter(post -> post.getId().equals(id)).findAny()
				.orElseThrow(() -> new RuntimeException("Post with id:" + id + " is not found"));
	}

	@Override
	public void deleteByName(String postName) {
		if (posts.containsKey(postName)) {
			posts.remove(postName);
		} else {
			throw new RuntimeException("Category with " + postName + " name  is not found");
		}
	}
}
