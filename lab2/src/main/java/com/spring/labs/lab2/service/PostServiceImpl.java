package com.spring.labs.lab2.service;

import java.time.LocalDateTime;
import java.util.List; 
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.spring.labs.lab2.dao.PostDao;
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreatePostDto;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostDao dao;
	private final UserService userService;
	private final TopicService topicService;

	public List<Post> getAllPosts() {
		return dao.findAll();
	} 

	public void createPost(Post post) {
		dao.save(post);
	}


	@Override
	public Post update(CreatePostDto createPost, Long postId) {
		Post post = findById(postId);
		post = post.toBuilder()
				.name(createPost.getName())
				.author(userService.findUserByName(createPost.getAuthor()))
				.description(createPost.getDescription())
				.content(createPost.getContent())
				.build();
		return dao.save(post);
	}

	@Override
	public void deleteByName(String postName) {
		dao.deleteByName(postName);
	}

	@Override
	public List<Post> findByTopicName(String topicTitle) {
		return dao.findAll().stream().filter(post -> post.getTopic().getTitle().equals(topicTitle))
				.collect(Collectors.toList());
	}

	@Override
	public Post findById(Long id) {
		return dao.findById(id);
	} 

	@Override
	public void generateDefaultPosts(Integer size, Faker faker) {
		Random random = new Random();
		int maxUpvotes = 10000;
		int minUpvotes = 100;
		int maxDownvotes = 500;
		int minDownvotes = 3;
		List<String> postNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
		List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
		List<Topic> topics = topicService.findAll();
		IntStream.range(1, size + 1).mapToObj(index -> Post.builder()
				.creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3))).name(postNames.get(index))
				.content(faker.lorem().sentence(50)).description(faker.lorem().sentence(2))
				.author(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
				.topic(topics.get(random.nextInt(0, topics.size())))
				.upvotes(random.nextInt(maxUpvotes - minUpvotes + 1) + minUpvotes)
				.downvotes(random.nextInt(maxDownvotes - minDownvotes + 1) + minDownvotes).build()).forEach(dao::save);
	}

	@Override
	public Post findByPostName(String postName) {
		return dao.findByName(postName);
	}

	@Override
	public Post createPost(CreatePostDto postDto, String topicTitle) {
        Post post = Post.builder()
                .content(postDto.getContent())
                .description(postDto.getDescription()) 
                .name(postDto.getName()) 
                .author(userService.findUserByName(postDto.getAuthor()))
                .creationDate(LocalDateTime.now())
                .topic((Topic) topicService.findByName(topicTitle))
                .build();
        dao.save(post);
        return post;
	}
}