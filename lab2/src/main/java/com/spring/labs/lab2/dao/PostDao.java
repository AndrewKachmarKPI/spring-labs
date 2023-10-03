package com.spring.labs.lab2.dao;

import java.util.List;

import com.spring.labs.lab2.domain.Post;

public interface PostDao {

	void save(Post post);

	List<Post> findAll();

	Post findById(Long id);

	void deleteByName(String postName);
}