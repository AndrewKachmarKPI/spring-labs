package com.spring.labs.lab4.serviceImpl;

import com.spring.labs.lab4.dao.TopicDao;
import com.spring.labs.lab4.domain.ForumCategory;
import com.spring.labs.lab4.domain.Topic;
import com.spring.labs.lab4.domain.User;
import com.spring.labs.lab4.dto.CreateTopicDto;
import com.spring.labs.lab4.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab4.exceptions.ResourceNotFoundException;
import com.spring.labs.lab4.service.ForumCategoryService;
import com.spring.labs.lab4.service.TopicService;
import com.spring.labs.lab4.service.UserService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicDao topicDao;
    private final UserService userService;
    private final ForumCategoryService categoryService;

    @Override
    public Topic create(CreateTopicDto createTopic, String categoryName) {
        if (topicDao.existByTitle(createTopic.getTitle())) {
            throw new ResourceAlreadyExistsException("Topic with title " + createTopic.getTitle() + " already exists");
        }
        Topic topic = Topic.builder()
                .title(createTopic.getTitle())
                .content(createTopic.getContent())
                .author(userService.findUserByName(createTopic.getAuthor()))
                .creationDate(LocalDateTime.now())
                .forumCategory(categoryService.findByName(categoryName))
                .build();
        return topicDao.save(topic);
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
    public Topic updateTopic(CreateTopicDto createTopic, Long id) {
        if (topicDao.existByTitle(createTopic.getTitle())) {
            throw new ResourceAlreadyExistsException("Topic with title " + createTopic.getTitle() + " already exists");
        }
        Topic topic = findById(id);
        topic = topic.toBuilder()
                .title(createTopic.getTitle())
                .content(createTopic.getContent())
                .author(userService.findUserByName(createTopic.getAuthor()))
                .build();
        topicDao.save(topic);
        return topic;
    }

    @Override
    public void deleteTopic(String title) {
        if (!topicDao.existByTitle(title)) {
            throw new ResourceNotFoundException("Topic with name " + title + " is not found");
        }
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
    public Topic findByName(String title) {
        return topicDao.findByName(title)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with title:" + title + " is not found"));
    }

    @Override
    public List<Topic> findAll() {
        return topicDao.findAll();
    }
}
