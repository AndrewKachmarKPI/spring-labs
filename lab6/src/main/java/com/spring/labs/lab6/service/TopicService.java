package com.spring.labs.lab6.service;

import com.spring.labs.lab6.dto.TopicDto;
import com.spring.labs.lab6.dto.create.CreateTopicDto;

import java.util.List;

public interface TopicService {
    TopicDto create(CreateTopicDto topicDto, String categoryName);

    TopicDto updateTopic(CreateTopicDto topicDto, Long id);

    List<TopicDto> findAll();

    List<TopicDto> findAll(String categoryName);

    void deleteTopic(String title);

    TopicDto findById(Long id);

    TopicDto findByName(String title);

}
