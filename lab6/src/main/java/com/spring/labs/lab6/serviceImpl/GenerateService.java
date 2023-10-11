package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.enums.UserRole;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.repositories.ForumCategoryRepository;
import com.spring.labs.lab6.repositories.UserRepository;
import com.spring.labs.lab6.service.DefaultGenerateMethods;
import com.spring.labs.lab6.service.ForumCategoryService;
import com.spring.labs.lab6.service.UserService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GenerateService implements DefaultGenerateMethods {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ForumCategoryRepository forumCategoryRepository;


    @Override
    public void generateDefaultCategories(Integer size, Faker faker) {
        List<String> categoryNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> usernames = userService.findAll().stream().map(UserDto::getUsername).toList();
        IntStream.range(1, size + 1).mapToObj(index -> ForumCategoryEntity.builder()
                .created(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .moderator(findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .build()).forEach(forumCategoryRepository::save);
    }

    @Override
    public void generateDefaultUsers(Integer size, Faker faker) {
        List<String> userNames = Stream.generate(() -> faker.name().username()).distinct().limit(size).toList();
        IntStream.range(1, size).mapToObj(index -> UserEntity.builder()
                .id((long) index)
                .username(userNames.get(index))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .profilePicture(faker.avatar().image())
                .biography(faker.lorem().sentence())
                .registrationDate(LocalDateTime.now())
                .role(UserRole.REGULAR_USER)
                .build()).forEach(userRepository::save);
    }

    private UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + username + " is not found"));
    }
}
