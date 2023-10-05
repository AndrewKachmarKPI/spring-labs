package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FakeTopicDao implements TopicDao {
    private final List<Topic> topics = new ArrayList<>();

    @Override
    public void save(Topic topic) {
        UUID uuid = UUID.randomUUID();
        if (Optional.ofNullable(topic.getId()).isEmpty()) {
            topic.setId(uuid.getLeastSignificantBits());
        } else {
            Topic topic1 = findById(topic.getId());
            topics.remove(topic1);
        }
        topics.add(topic);
    }

    @Override
    public List<Topic> findAll() {
        return topics.stream().sorted(Comparator.comparing(Topic::getCreationDate).reversed()).toList();
    }

    @Override
    public Topic findById(Long id) {
        return topics.stream()
                .filter(topic -> topic.getId().equals(id)).findAny()
                .orElseThrow(() -> new RuntimeException("Topic with id:" + id + " is not found"));
    }

    @Override
    public void deleteTopic(String title) {
        topics.removeIf(topic -> topic.getTitle().equals(title));
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
