package com.spring.labs.lab5.dao;

import com.spring.labs.lab5.domain.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicDao {
    Topic save(Topic topic);
    List<Topic> findAll();
    Topic findById(Long id);
    void deleteTopic(String title);
    Optional<Topic> findByName(String title);
    boolean existByTitle(String title);
}
