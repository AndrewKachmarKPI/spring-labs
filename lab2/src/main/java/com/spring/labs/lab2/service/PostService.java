package com.spring.labs.lab2.service;

import java.util.List; 

import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.dto.CreatePostDto; 
import net.datafaker.Faker;

public interface PostService {

	public List<Post> findAll();

	public Post getById(Long id);

	public Post createPost(CreatePostDto post);

	public Post update(CreatePostDto post, Long id);

	void deleteByName(String postName);

	Post findById(Long id);

	Post changeAuthor(String postName, String username);

	void generateDefaultPosts(Integer size, Faker faker);

	List<Post> findByTopicName(String topicName);

	public Object findByPostName(String name);

	public Post createPost(CreatePostDto post, String topicTitle);
}
