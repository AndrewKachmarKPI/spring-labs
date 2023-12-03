package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.domain.PostEntity;
import com.spring.labs.lab6.domain.TopicEntity;
import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.enums.UserRole;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.mapper.BusinessMapper;
import com.spring.labs.lab6.repositories.ForumCategoryRepository;
import com.spring.labs.lab6.repositories.PostRepository;
import com.spring.labs.lab6.repositories.TopicRepository;
import com.spring.labs.lab6.repositories.UserRepository;
import com.spring.labs.lab6.service.DefaultGenerateMethods;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GenerateService implements DefaultGenerateMethods {
    private final BusinessMapper businessMapper;
    private final UserRepository userRepository;
    private final ForumCategoryRepository forumCategoryRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;


    @Override
    public void generateDefaultCategories(Integer size, Faker faker) {
        List<String> categoryNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<UserDto> users = generateDefaultUsers(size + 1, faker);
        IntStream.range(0, size).mapToObj(index -> ForumCategoryEntity.builder()
                .created(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .moderator(businessMapper.getUserEntity(users.get(index)))
                .build()).forEach(forumCategoryRepository::save);
    }

    @Override
    public List<UserDto> generateDefaultUsers(Integer size, Faker faker) {
        List<String> userNames = Stream.generate(() -> faker.name().username()).distinct().limit(size).toList();
        return IntStream.range(1, size).mapToObj(index -> UserDto.builder()
                .username(userNames.get(index))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .profilePicture(faker.avatar().image())
                .biography(faker.lorem().sentence())
                .registrationDate(LocalDateTime.now())
                .role(UserRole.REGULAR_USER)
                .build()).collect(Collectors.toList());
    }

    @Override
    public void generateDefaultTopics(Integer size, Faker faker) {
        List<String> topicTitles = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<UserDto> authors = generateDefaultUsers(size + 1, faker);
        List<ForumCategoryEntity> categories = forumCategoryRepository.findAll();
        IntStream.range(0, size).mapToObj(index -> TopicEntity.builder()
                .creationDate(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .title(topicTitles.get(index))
                .content(faker.lorem().sentence())
                .author(businessMapper.getUserEntity(authors.get(index)))
                .forumCategory(categories.get(new Random().nextInt(0, categories.size())))
                .build()).forEach(topicRepository::save);
    }

    @Override
    public void generateDefaultPosts(Integer size, Faker faker) {
        Random random = new Random();
        int maxUpVotes = 10000;
        int minUpVotes = 100;
        int maxDownVotes = 500;
        int minDownVotes = 3;
        int contentSentences = 10;
        int postDescriptionSize = 1;
        int postNameSize = 1;
        List<UserDto> authors = generateDefaultUsers(size + 1, faker);
        List<TopicEntity> topics = topicRepository.findAll();
        IntStream.range(0, size).mapToObj(index -> PostEntity.builder()
                .name(faker.lorem().sentence(postNameSize))
                .description(faker.lorem().sentence(postDescriptionSize))
                .creationDate(LocalDateTime.now().minusDays(random.nextInt(0, 3)))
                .content(faker.lorem().sentence(contentSentences))
                .author(businessMapper.getUserEntity(authors.get(index)))
                .topic(topics.get(random.nextInt(0, topics.size())))
                .upVotes(random.nextInt(maxUpVotes - minUpVotes + 1) + minUpVotes)
                .downVotes(random.nextInt(maxDownVotes - minDownVotes + 1) + minDownVotes)
                .build()).forEach(postRepository::save);
    }

    private UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + username + " is not found"));
    }
}
