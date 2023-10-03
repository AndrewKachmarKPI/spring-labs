package com.spring.labs.lab2.service;

import java.util.List;
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.domain.User;

public interface PostService {

	public List<Post> getAllPosts();

	public Post getPostById(Long id);

	public void createPost(Post post);

	public void deletePost(Long id);

	public void updatePost(Long id, String content,User author, String name, int upvotes, int downvotes);
}
