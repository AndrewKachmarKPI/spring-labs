package com.spring.labs.lab2.dao;

import java.util.List;

import com.spring.labs.lab2.domain.Post;

public interface PostDao {

	Post save(Post post);

	List<Post> findAll();

	Post findById(Long id); 

	Post findByName(String postName);

    boolean existByName(String categoryName);
	
	void deleteByName(String postName);
	
 }