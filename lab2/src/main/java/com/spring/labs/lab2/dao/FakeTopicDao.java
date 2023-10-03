package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FakeTopicDao implements TopicDao {
    private final List<Topic> topics = new ArrayList<>();

    @Override
    public void save(Topic topic) {
        topics.add(topic);
        for (Topic topic1 : topics) {
            topic1.setId((long) topics.indexOf(topic1));
        }
    }

    @Override
    public List<Topic> findAll() {
        return topics.stream().sorted(Comparator.comparing(Topic::getCreationDate).reversed()).toList();
//        return topics;
    }

    @Override
    public Topic findById(Long id) {
        return topics.stream()
                .filter(topic -> topic.getId().equals(id)).findAny()
                .orElseThrow(() -> new RuntimeException("Topic with id:" + id + " is not found"));
    }

    @Override
    public void deleteTopic(Topic topic) {
        if (!topics.contains(topic)) {
            throw new RuntimeException("Topic is not found");
        }
        topics.remove(topic);
    }
    @Override
    public Object findByName(String title) {
        for (Topic topic : topics) {
            if (topic.getTitle().equals(title)) {
                return topic;
            }
        }
        return "Topic is not found";
    }
}
