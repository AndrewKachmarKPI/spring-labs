package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.ForumCategoryDao;
import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ForumCategoryServiceImpl implements ForumCategoryService {
    private final ForumCategoryDao dao;
    private final UserService userService;
    private final Faker faker;

    @Override
    public ForumCategory create(CreateForumCategoryDto createForumCategory) {
        if (dao.existByName(createForumCategory.getCategoryName())) {
            throw new RuntimeException("Category with name " + createForumCategory.getCategoryName() + " already exists");
        }
        ForumCategory forumCategory = ForumCategory.builder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .moderator(userService.findUserByName(createForumCategory.getUsername()))
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .created(LocalDateTime.now())
                .build();

        return dao.save(forumCategory);
    }

    @Override
    public ForumCategory update(CreateForumCategoryDto createForumCategory, Long categoryId) {
        ForumCategory forumCategory = findById(categoryId);
        forumCategory = forumCategory.toBuilder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .moderator(userService.findUserByName(createForumCategory.getUsername()))
                .build();
        return dao.save(forumCategory);
    }

    @Override
    public List<ForumCategory> findAll() {
        return dao.findAll();
    }

    @Override
    public void deleteByName(String categoryName) {
        dao.removeByName(categoryName);
    }

    @Override
    public ForumCategory findByName(String categoryName) {
        return dao.findByName(categoryName);
    }

    @Override
    public ForumCategory findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public ForumCategory changeModerator(String categoryName, String username) {
        ForumCategory forumCategory = findByName(categoryName);
        User user = userService.findUserByName(username);
        forumCategory = forumCategory.toBuilder()
                .moderator(user)
                .build();
        return dao.save(forumCategory);
    }

    @Override
    public void generateDefaultCategories(Integer size, Faker faker) {
        List<String> categoryNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
        IntStream.range(1, size + 1).mapToObj(index -> ForumCategory.builder()
                .created(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .moderator(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .build()).forEach(dao::save);
    }
}
