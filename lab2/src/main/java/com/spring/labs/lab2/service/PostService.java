package com.spring.labs.lab2.service;

import java.util.List;

import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.dto.CreatePostDto;
import net.datafaker.Faker;

public interface PostService {
	
	public Post update(CreatePostDto post, Long id);

	public void deleteByName(String postName);

	public Post findById(Long id);

	public void generateDefaultPosts(Integer size, Faker faker);

	public List<Post> findByTopicName(String topicName);

	public Object findByPostName(String name);

	public Post createPost(CreatePostDto post, String topicTitle);
}
