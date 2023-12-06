package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.TopicEntity;
import com.spring.labs.lab6.dto.TopicDto;
import com.spring.labs.lab6.dto.create.CreateTopicDto;
import com.spring.labs.lab6.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.mapper.BusinessMapper;
import com.spring.labs.lab6.repositories.ForumCategoryRepository;
import com.spring.labs.lab6.repositories.TopicRepository;
import com.spring.labs.lab6.repositories.UserRepository;
import com.spring.labs.lab6.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final ForumCategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final BusinessMapper businessMapper;
    @Override
    @Transactional
    public TopicDto create(CreateTopicDto createTopic, String categoryName, String userName) {
        if (topicRepository.existsByTitle(createTopic.getTitle())) {
            throw new ResourceAlreadyExistsException("Topic with title " + createTopic.getTitle() + " already exists");
        }
        TopicEntity topic = TopicEntity.builder()
                .title(createTopic.getTitle())
                .content(createTopic.getContent())
                .author(userRepository.findByUsername(userName)
                        .orElseThrow(() -> new ResourceNotFoundException("User with name:" + userName + " is not found")))
                .creationDate(LocalDateTime.now())
                .forumCategory(categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new ResourceNotFoundException("Category with name:" + categoryName + " is not found")))
                .build();
        return businessMapper.getTopic(topicRepository.save(topic));
    }

    @Override
    public TopicDto updateTopic(CreateTopicDto createTopic, Long id) {
        if (topicRepository.existsByTitle(createTopic.getTitle())) {
            throw new ResourceAlreadyExistsException("Topic with title " + createTopic.getTitle() + " already exists");
        }
        TopicEntity topic = findEntityById(id);
        topic = topic.toBuilder()
                .title(createTopic.getTitle())
                .content(createTopic.getContent())
                .build();
        return businessMapper.getTopic(topicRepository.save(topic));
    }

    @Override
    public List<TopicDto> findAll() {
        return businessMapper.collectionToList(topicRepository.findAll(), businessMapper.topicEntityToDto);
    }

    @Override
    public List<TopicDto> findAll(String categoryName) {
        return businessMapper.collectionToList(topicRepository.findAllByForumCategoryCategoryName(categoryName), businessMapper.topicEntityToDto);
    }

    @Override
    @Transactional
    public void deleteTopic(String title) {
        if (!topicRepository.existsByTitle(title)) {
            throw new ResourceNotFoundException("Topic with name " + title + " is not found");
        }
        topicRepository.deleteByTitle(title);
    }

    @Override
    public TopicDto findById(Long id) {
        TopicEntity topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id:" + id + " is not found"));
        return businessMapper.getTopic(topic);
    }

    @Override
    public TopicDto findByName(String title) {
        TopicEntity topic = topicRepository.findByName(title)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with name:" + title + " is not found"));
        return businessMapper.getTopic(topic);
    }

    public TopicEntity findEntityById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id:" + id + " is not found"));
    }
}
