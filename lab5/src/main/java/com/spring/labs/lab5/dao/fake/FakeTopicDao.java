package com.spring.labs.lab5.dao.fake;

import com.spring.labs.lab5.dao.TopicDao;
import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FakeTopicDao implements TopicDao {
    private final List<Topic> topics = new ArrayList<>();

    @Override
    public Topic save(Topic topic) {
        if (Optional.ofNullable(topic.getId()).isEmpty()) {
            topic = topic.toBuilder().id((long) topics.size() + 1).build();
        } else {
            Topic topic1 = findById(topic.getId());
            topics.remove(topic1);
        }
        topics.add(topic);
        return topic;
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
    public Optional<Topic> findByName(String title) {
        if (!existByTitle(title)) {
            throw new ResourceNotFoundException("Topic with title " + title + " is not found");
        }
        for (Topic topic : topics) {
            if (topic.getTitle().equals(title)) {
                return Optional.of(topic);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existByTitle(String title) {
        return topics.stream().anyMatch(topic-> topic.getTitle().equals(title));
    }
}
