package com.spring.labs.lab2.service;

import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.dto.CreateTopicDto;

import java.util.List;

public interface TopicService {
    Topic create(CreateTopicDto topicDto);

    List<Topic> findAll();

    Topic findById(Long id);

    void updateTopic(CreateTopicDto topicDto, Long id);

    void deleteTopic(Long id);
}