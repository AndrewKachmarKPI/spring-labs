package com.spring.labs.lab2.service;

import java.time.LocalDateTime;
import java.util.List; 
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
 
import org.springframework.stereotype.Service;
 
import com.spring.labs.lab2.dao.PostDao; 
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.domain.User; 
import com.spring.labs.lab2.dto.CreatePostDto;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostDao dao;
	private final UserService userService;
	private final Faker faker;

	public List<Post> getAllPosts() {
		return dao.findAll();
	}

	public Post getById(Long id) {
		return dao.findById(id);
	}

	public void createPost(Post post) {
		dao.save(post);
	}

	@Override
	public List<Post> findAll() {
		return dao.findAll();
	}

	@Override
	public Post createPost(CreatePostDto createPost) {
		if (dao.existByName(createPost.getName())) {
			throw new RuntimeException("Category with name " + createPost.getName() + " already exists");
		}
		Post post = Post.builder().content(createPost.getContent()).name(createPost.getName())
				.author(userService.findUserByName(createPost.getUsername())).creationDate(LocalDateTime.now()).build();

		return dao.save(post);
	}

	@Override
	public Post update(CreatePostDto createPost, Long postId) {
		Post post = findById(postId);
		post = post.toBuilder().content(createPost.getContent()).name(createPost.getName())
				.author(userService.findUserByName(createPost.getUsername())).build();
		return dao.save(post);
	}

	@Override
	public void deleteByName(String postName) {
		dao.deleteByName(postName);
	}

	@Override
	public Post findByName(String postName) {
		return dao.findByName(postName);
	}

	@Override
	public Post findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public Post changeAuthor(String postName, String username) {
		Post post = findByName(postName);
		User user = userService.findUserByName(username);
		post = post.toBuilder().author(user).build();
		return dao.save(post);
	}

	@Override
	public void generateDefaultPosts(Integer size, Faker faker) {
		List<String> postNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
		List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
		IntStream.range(1, size + 1).mapToObj(index -> Post.builder()
				.creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3))).name(postNames.get(index))
				.content(faker.lorem().sentence())
				.author(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size())))).build())
				.forEach(dao::save);
	}

}