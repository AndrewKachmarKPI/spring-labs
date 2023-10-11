package com.spring.labs.lab5.serviceImpl;

import com.spring.labs.lab5.dao.ForumCategoryDao;
import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.domain.User;
import com.spring.labs.lab5.dto.CreateForumCategoryDto;
import com.spring.labs.lab5.dto.PageDto;
import com.spring.labs.lab5.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab5.exceptions.ResourceNotFoundException;
import com.spring.labs.lab5.service.ForumCategoryService;
import com.spring.labs.lab5.service.UserService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {
    private final ForumCategoryDao dao;
    private final UserService userService;
    private final Faker faker;

    public ForumCategoryServiceImpl(@Qualifier("jdbcCategoryDao") ForumCategoryDao dao,
                                    UserService userService, Faker faker) {
        this.dao = dao;
        this.userService = userService;
        this.faker = faker;
    }

    @Override
    @Transactional
    public ForumCategory create(CreateForumCategoryDto createForumCategory) {
        if (dao.existByName(createForumCategory.getCategoryName())) {
            throw new ResourceAlreadyExistsException("Category with name " + createForumCategory.getCategoryName() + " already exists");
        }
        ForumCategory forumCategory = ForumCategory.builder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .moderator(userService.saveUser(createForumCategory.getCreateUserDto()))
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .created(LocalDateTime.now().toString())
                .build();
        return dao.save(forumCategory);
    }

    @Override
    public ForumCategory update(Long categoryId, CreateForumCategoryDto createForumCategory) {
        ForumCategory forumCategory = findById(categoryId);
        forumCategory = forumCategory.toBuilder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .moderator(userService.findUserByName(createForumCategory.getUsername()))
                .build();
        return dao.save(forumCategory);
    }

    @Override
    public PageDto<ForumCategory> findAll(Integer pageNumber, Integer pageSize, String title) {
        List<ForumCategory> categories = dao.findAllPageableAndFiltered(pageNumber, pageSize, title);
        PageDto<ForumCategory> forumCategoryPageDto = PageDto.<ForumCategory>builder()
                .data(categories)
                .page(pageNumber)
                .size(pageSize)
                .totalSize(dao.totalSize())
                .build();
        if (!isNull(pageNumber) && !isNull(pageSize)) {
            forumCategoryPageDto = forumCategoryPageDto.toBuilder()
                    .totalPage((int) Math.ceil((double) dao.totalSize() / pageSize))
                    .build();
        }
        return forumCategoryPageDto;
    }

    @Override
    public List<ForumCategory> findAll() {
        return dao.findAll();
    }

    @Override
    public void deleteByName(String categoryName) {
        if (!dao.existByName(categoryName)) {
            throw new ResourceAlreadyExistsException("Category with name " + categoryName + " is not found");
        }
        dao.removeByName(categoryName);
    }

    @Override
    public ForumCategory findByName(String categoryName) {
        return dao.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name:" + categoryName + " is not found"));
    }

    @Override
    public ForumCategory findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id:" + id + " is not found"));
    }

    @Override
    public void generateDefaultCategories(Integer size, Faker faker) {
        List<String> categoryNames = Stream.generate(() -> faker.lorem().sentence(2)).distinct().limit(size + 1).toList();
        List<String> usernames = userService.findAll().stream().map(User::getUsername).toList();
        IntStream.range(1, size + 1).mapToObj(index -> ForumCategory.builder()
                .created(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)).toString())
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .moderator(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .build()).forEach(dao::save);
    }
}
