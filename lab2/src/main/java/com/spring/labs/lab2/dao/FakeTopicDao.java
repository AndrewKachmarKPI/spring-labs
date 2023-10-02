package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.Topic;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class FakeTopicDao implements TopicDao {
    private List<Topic> topics;

    @Override
    public void save(Topic topic) {
        topics.add(topic);
    }

    @Override
    public List<Topic> findAll() {
        return new ArrayList<>(topics);
    }

    @Override
    public Topic findById(Long id) {
        Topic foundTopic = find(id);
        if (foundTopic == null) {
            return null;
        }
        return new Topic(foundTopic.getId(), foundTopic.getTitle(), foundTopic.getContent(), foundTopic.getAuthor(),
                foundTopic.getCreationDate(), foundTopic.getLastUpdatedDate(), foundTopic.getViews(),
                foundTopic.getTags(), foundTopic.getUpVotes(), foundTopic.getDownVotes(),
                foundTopic.isLocked(), foundTopic.getForumCategory());
    }

    private Topic find(Long id) {
        for (Topic topic : topics) {
            if (Objects.equals(id, topic.getId())) {
                return topic;
            }
        }
        return null;
    }

    @Override
    public void deleteTopic(Long id) {
        Topic topic = find(id);
        if (topic != null) {
            topics.remove(topic);
            System.out.println("Topic: " + id + " deleted");
        } else {
            System.out.println("Topic: " + id + " not found");
        }
    }
}
