package com.spring.labs.lab2.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.labs.lab2.dao.PostDao;
import com.spring.labs.lab2.dao.PostRepository;
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.domain.User;

@Service
public class PostServiceImpl implements PostService {

	private final PostDao dao;

	@Autowired
	public PostServiceImpl(PostDao dao) {
		this.dao = dao;
	}

	public List<Post> getAllPosts() {
		return dao.findAll();
	}

	public Post getPostById(Long id) {
		return dao.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Post with ID: " + id + " not found"));
	}

	public void createPost(Post post) {
		dao.save(post);
	}

	public void deletePost(Long id) {
		dao.deleteById(id);
	}

	@Transactional
	public void updatePost(Long id, String content, User author, String name, int upvotes, int downvotes) {
		Post post = dao.findById(id)
				.orElseThrow(() -> new IllegalStateException("Post with " + id + " does not exist"));
		if (content != null && content.length() > 0) {
			post.setContent(content);
		}
		if (author !=null) {
			post.setAuthor(author);
		}
		if (name != null && name.length() > 0) {
			post.setName(name);
		}
		if (upvotes >= 0) {
			post.setUpvotes(upvotes);
		}
		if (downvotes >= 0) {
			post.setDownvotes(downvotes);
		}
	}
}