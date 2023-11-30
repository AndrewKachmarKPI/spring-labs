package com.spring.labs.lab5.service;

import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.dto.CreateTopicDto;
import net.datafaker.Faker;

import java.util.List;

public interface TopicService {
	Topic create(CreateTopicDto topicDto, String categoryName);

	List<Topic> findAll(String categoryName);

	Topic findById(Long id);

	Topic updateTopic(CreateTopicDto topicDto, Long id);

	void deleteTopic(String title);

	void generateDefaultTopics(Integer size, Faker faker);

	Topic findByName(String title);

	List<Topic> findAll();
}
