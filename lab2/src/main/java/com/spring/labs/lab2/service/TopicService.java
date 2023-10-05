package com.spring.labs.lab2.service;

import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.dto.CreateTopicDto;
import net.datafaker.Faker;

import java.util.List;

public interface TopicService {
    Topic create(CreateTopicDto topicDto, String categoryName);

    List<Topic> findAll(String categoryName);

    Topic findById(Long id);

    void updateTopic(CreateTopicDto topicDto, Long id);

    void deleteTopic(String title);

    void generateDefaultTopics(Integer size, Faker faker);

    Object findByName(String title);
}
