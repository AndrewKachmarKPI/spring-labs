package com.spring.labs.lab5.serviceImpl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.spring.labs.lab5.dao.PostDao;
import com.spring.labs.lab5.exceptions.ResourceNotFoundException;
import com.spring.labs.lab5.service.PostService;
import com.spring.labs.lab5.service.TopicService;
import com.spring.labs.lab5.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.labs.lab5.domain.Post;
import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.domain.User;
import com.spring.labs.lab5.dto.CreatePostDto;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Service
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final UserService userService;
    private final TopicService topicService;

    public PostServiceImpl(@Qualifier("jdbcPostDao") PostDao postDao, UserService userService, TopicService topicService) {
        this.postDao = postDao;
        this.userService = userService;
        this.topicService = topicService;
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
        return postDao.save(post);
    }

    @Override
    public void deleteByName(String postName) {
        postDao.deleteByName(postName);
    }

    @Override
    public List<Post> findByTopicName(String topicTitle) {
        return postDao.findAll().stream().filter(post -> post.getTopic().getTitle().equals(topicTitle))
                .collect(Collectors.toList());
    }

    @Override
    public Post findById(Long id) {
        return postDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found"));
    }

    @Override
    public void generateDefaultPosts(Integer size, Faker faker) {
        Random random = new Random();
        int maxUpVotes = 10000;
        int minUpVotes = 100;
        int maxDownVotes = 500;
        int minDownVotes = 3;
        int contentSentences = 10;
        List<String> postNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
        List<Topic> topics = topicService.findAll();
        IntStream.range(1, size + 1).mapToObj(index -> Post.builder()
                .creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3))).name(postNames.get(index))
                .content(faker.lorem().sentence(contentSentences)).description(faker.lorem().sentence(2))
                .author(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .topic(topics.get(random.nextInt(0, topics.size())))
                .upVotes(random.nextInt(maxUpVotes - minUpVotes + 1) + minUpVotes)
                .downVotes(random.nextInt(maxDownVotes - minDownVotes + 1) + minDownVotes).build()).forEach(postDao::save);
    }

    @Override
    public Post findByPostName(String postName) {
        return postDao.findByName(postName).orElseThrow(() -> new ResourceNotFoundException("Post with name:" + postName + " is not found"));
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
        postDao.save(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return  postDao.findAll();
    }
}