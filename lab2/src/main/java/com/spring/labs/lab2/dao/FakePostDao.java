package com.spring.labs.lab2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.spring.labs.lab2.domain.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FakePostDao implements PostDao {

	private final Map<String, Post> posts = new HashMap<>();

	@Override
	public Post save(Post savedPost) {
		UUID uuid = UUID.randomUUID();
		if (Optional.ofNullable(savedPost.getId()).isEmpty()) {
			savedPost.setId(uuid.getLeastSignificantBits());
		} else {
			Post post = findById(savedPost.getId());
			posts.remove(post.getName());
		}
		return posts.put(savedPost.getName(), savedPost);
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
	public Post findByName(String name) {
		if (!existByName(name)) {
			throw new RuntimeException("Post is not found");
		}
		return posts.get(name);
	}

	@Override
	public Post findByTopicName(String topicName) {
		Optional<Post> matchingPost = posts.values().stream()
				.filter(post -> post.getTopic() != null && topicName.equals(post.getTopic().getTitle())).findFirst();
		return matchingPost.orElse(null);
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
