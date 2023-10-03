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

	public Post findByName(String postName);

	void deleteByName(String postName);

	Post findById(Long id);

	Post changeAuthor(String postName, String username);

	void generateDefaultPosts(Integer size, Faker faker);
}
