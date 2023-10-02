package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.FakeTopicDao;
import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.dto.CreateTopicDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class TopicServiceImpl implements TopicService {
    private FakeTopicDao topicDao;

    @Override
    public Topic create(CreateTopicDto topicDto) {
        Topic topic = Topic.builder()
                .title(topicDto.getTitle())
                .content(topicDto.getContent())
                .author(topicDto.getAuthor())
                .forumCategory(topicDto.getForumCategory())
                .creationDate(LocalDateTime.now())
                .build();
        topicDao.save(topic);
        return topic;
    }

    @Override
    public List<Topic> findAll() {
        return topicDao.findAll();
    }

    @Override
    public Topic findById(Long id) {
        return topicDao.findById(id);
    }

    @Override
    public void updateTopic(CreateTopicDto topicDto, Long id) {
        Topic topic = findById(id);
        topic = topic.toBuilder().title(topicDto.getTitle())
                .content(topicDto.getContent())
                .author(topicDto.getAuthor())
                .forumCategory(topicDto.getForumCategory())
                .build();
        topicDao.save(topic);
    }

    @Override
    public void deleteTopic(Long id) {
        topicDao.deleteTopic(id);
    }
}
