package com.spring.labs.lab2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.spring.labs.lab2.domain.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FakePostDao implements PostDao {

	private final Map<String, Post> posts = new HashMap<>();

	@Override
	public Post save(Post post) {
		if (Optional.ofNullable(post.getId()).isEmpty()) {
			post = post.toBuilder().id((long) posts.size() + 1).build();
		} else {
			post = findById(post.getId());
			posts.remove(post.getName());
		}
		return posts.put(post.getName(), post);
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
	public Post findByName(String postName) {
		if (!existByName(postName)) {
			throw new RuntimeException("Post is not found");
		}
		return posts.get(postName);
	}

	@Override
	public void deleteByName(String postName) {
		if (!existByName(postName)) {
			throw new RuntimeException("Post with " + postName + " name  is not found");
		}
		posts.remove(postName);
	}

	@Override
	public boolean existByName(String postName) {
		return posts.containsKey(postName);
	}

}
