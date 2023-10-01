package com.spring.labs.lab2.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/")
	public List<Post> getAllPosts() {
		return postService.getAllPosts();
	}

	@GetMapping("/{id}")
	public Post getPostById(@PathVariable Long id) {
		return postService.getPostById(id);
	}

	@PostMapping("/")
	public void createPost(@RequestBody Post post) {
		postService.createPost(post);
	}

	@PutMapping("/{id}")
	public void updatePost(@PathVariable Long id, @RequestParam(required = false) String content,
			@RequestParam(required = false) int upvotes, @RequestParam(required = false) int downvotes) {
		postService.updatePost(id, content, upvotes, downvotes);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable Long id) {
		postService.deletePost(id);
	}
}