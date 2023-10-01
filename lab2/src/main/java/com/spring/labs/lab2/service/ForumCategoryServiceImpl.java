package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.ForumCategoryDao;
import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumCategoryServiceImpl implements ForumCategoryService {
    private final ForumCategoryDao dao;
    private final UserService userService;

    @Override
    public ForumCategory create(CreateForumCategoryDto createForumCategory) {
        if (dao.existByName(createForumCategory.getCategoryName())) {
            throw new RuntimeException("Category with name " + createForumCategory.getCategoryName() + " already exists");
        }
        ForumCategory forumCategory = ForumCategory.builder()
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
    public ForumCategory changeModerator(String categoryName, String username) {
        ForumCategory forumCategory = findByName(categoryName);
        User user = userService.findUserByName(username);
        forumCategory = forumCategory.toBuilder()
                .moderator(user)
                .build();
        return dao.save(forumCategory);
    }
}
