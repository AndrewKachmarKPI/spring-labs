package com.spring.labs.lab4.serviceImpl;


import com.spring.labs.lab4.dao.PostDao;
import com.spring.labs.lab4.domain.Post;
import com.spring.labs.lab4.domain.Topic;
import com.spring.labs.lab4.domain.User;
import com.spring.labs.lab4.dto.CreatePostDto;
import com.spring.labs.lab4.exceptions.ResourceNotFoundException;
import com.spring.labs.lab4.service.PostService;
import com.spring.labs.lab4.service.TopicService;
import com.spring.labs.lab4.service.UserService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao dao;
    private final UserService userService;
    private final TopicService topicService;

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
        return dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found"));
    }

    @Override
    public void generateDefaultPosts(Integer size, Faker faker) {
        Random random = new Random();
        int maxUpVotes = 10000;
        int minUpVotes = 100;
        int maxDownVotes = 500;
        int minDownVotes = 3;
        int contentSentences = 400;
        List<String> postNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
        List<Topic> topics = topicService.findAll();
        IntStream.range(1, size + 1).mapToObj(index -> Post.builder()
                .creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3))).name(postNames.get(index))
                .content(faker.lorem().sentence(contentSentences)).description(faker.lorem().sentence(2))
                .author(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .topic(topics.get(random.nextInt(0, topics.size())))
                .upVotes(random.nextInt(maxUpVotes - minUpVotes + 1) + minUpVotes)
                .downVotes(random.nextInt(maxDownVotes - minDownVotes + 1) + minDownVotes).build()).forEach(dao::save);
    }

    @Override
    public Post findByPostName(String postName) {
        return dao.findByName(postName).orElseThrow(() -> new ResourceNotFoundException("Post with name:" + postName + " is not found"));
    }

    @Override
    public Post createPost(CreatePostDto postDto, String topicTitle) {
        Post post = Post.builder()
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .name(postDto.getName())
                .author(userService.findUserByName(postDto.getAuthor()))
                .creationDate(LocalDateTime.now())
                .topic(topicService.findByName(topicTitle))
                .build();
        return dao.save(post);
    }

    @Override
    public List<Post> findAll() {
        return dao.findAll();
    }
}
