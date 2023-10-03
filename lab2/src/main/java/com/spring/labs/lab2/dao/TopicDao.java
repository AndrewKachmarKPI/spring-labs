package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.dto.CreateTopicDto;
import net.datafaker.Faker;

import java.util.List;

public interface TopicDao {

    void save(Topic topic);

    List<Topic> findAll();

    Topic findById(Long id);

//    void updateTopic(CreateTopicDto topicDto, Long id);

    void deleteTopic(String title);

    Object findByName(String title);

//    void generateDefaultTopics(Integer size, Faker faker);

//    boolean exist(Topic topic);

//    Topic find(Topic topic);
}
