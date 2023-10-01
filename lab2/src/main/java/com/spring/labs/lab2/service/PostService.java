package com.spring.labs.lab2.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.labs.lab2.dao.PostRepository;
import com.spring.labs.lab2.domain.Post;

@Service
public class PostService {

	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public Post getPostById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Post with ID: " + id + " not found"));
	}

	public void createPost(Post post) {
		postRepository.save(post);
	}

	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

	@Transactional
	public void updatePost(Long id, String content, int upvotes, int downvotes) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Post with " + id + " does not exist"));
		if (content != null && content.length() > 0) {
			post.setContent(content);
		}
		if (upvotes >= 0) {
			post.setUpvotes(upvotes);
		}
		if (downvotes >= 0) {
			post.setDownvotes(downvotes);
		}
	}

}