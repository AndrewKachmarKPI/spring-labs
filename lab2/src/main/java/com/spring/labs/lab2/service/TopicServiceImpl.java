package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.FakeTopicDao;
import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreateTopicDto;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TopicServiceImpl implements TopicService {
    private final FakeTopicDao topicDao;
    private final UserService userService;
    private final ForumCategoryService categoryService;

    @Autowired
    public TopicServiceImpl(FakeTopicDao topicDao, UserService userService, ForumCategoryService categoryService) {
        this.topicDao = topicDao;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public Topic create(CreateTopicDto topicDto) {
        Topic topic = Topic.builder()
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .author(userService.findUserByName(topicDto.getAuthor()))
                .creationDate(LocalDateTime.now())
                .build();
        topicDao.save(topic);
        return topic;
    }


    @Override
    public List<Topic> findAll(String categoryName) {
        return topicDao.findAll().stream()
                .filter(topic -> topic.getForumCategory().getCategoryName().equals(categoryName))
                .toList();
    }

    @Override
    public Topic findById(Long id) {
        return topicDao.findById(id);
    }

    @Override
    public void updateTopic(CreateTopicDto topicDto, Long id) {
        Topic topic = findById(id);
        topic = topic.toBuilder()
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .author(userService.findUserByName(topicDto.getAuthor()))
                .build();
        topicDao.save(topic);
    }

    @Override
    public void deleteTopic(String title) {
        topicDao.deleteTopic(title);
    }

    @Override
    public void generateDefaultTopics(Integer size, Faker faker) {
        List<String> topicTitles = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> authorNames = userService.findAll().stream().map(User::getUsername).toList();
        List<ForumCategory> categories = categoryService.findAll();
        IntStream.range(1, size + 1).mapToObj(index -> Topic.builder()
                .creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .title(topicTitles.get(index))
                .content(faker.lorem().sentence())
                .author(userService.findUserByName(authorNames.get(new Random().nextInt(authorNames.size()))))
                .forumCategory(categories.get(new Random().nextInt(0, categories.size())))
                .build()).forEach(topicDao::save);
    }

    @Override
    public Object findByName(String title) {
        return topicDao.findByName(title);
    }
}
