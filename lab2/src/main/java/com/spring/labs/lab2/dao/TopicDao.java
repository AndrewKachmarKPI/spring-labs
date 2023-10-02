package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.Topic;

import java.util.List;

public interface TopicDao {
    void save(Topic topic);

    List<Topic> findAll();

    Topic findById(Long id);

    void deleteTopic(Long id);
}
