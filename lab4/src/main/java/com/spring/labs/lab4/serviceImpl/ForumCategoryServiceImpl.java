package com.spring.labs.lab4.serviceImpl;

import com.spring.labs.lab4.dao.ForumCategoryDao;
import com.spring.labs.lab4.domain.ForumCategory;
import com.spring.labs.lab4.domain.User;
import com.spring.labs.lab4.dto.CreateForumCategoryDto;
import com.spring.labs.lab4.dto.PageDto;
import com.spring.labs.lab4.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab4.exceptions.ResourceNotFoundException;
import com.spring.labs.lab4.service.ForumCategoryService;
import com.spring.labs.lab4.service.UserService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ForumCategoryServiceImpl implements ForumCategoryService {
    private final ForumCategoryDao dao;
    private final UserService userService;
    private final Faker faker;

    @Override
    public ForumCategory create(CreateForumCategoryDto createForumCategory) {
        if (dao.existByName(createForumCategory.getCategoryName())) {
            throw new ResourceAlreadyExistsException("Category with name " + createForumCategory.getCategoryName() + " already exists");
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
                .created(LocalDateTime.now().minusDays(new Random().nextInt(0, 3)))
                .categoryName(categoryNames.get(index))
                .description(faker.lorem().sentence())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .moderator(userService.findUserByName(usernames.get(new Random().nextInt(usernames.size()))))
                .build()).forEach(dao::save);
    }
}
