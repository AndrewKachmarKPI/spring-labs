package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.dto.ForumCategoryDto;
import com.spring.labs.lab6.dto.create.CreateForumCategoryDto;
import com.spring.labs.lab6.dto.PageDto;
import com.spring.labs.lab6.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.mapper.BusinessMapper;
import com.spring.labs.lab6.repositories.ForumCategoryRepository;
import com.spring.labs.lab6.service.ForumCategoryService;
import com.spring.labs.lab6.service.UserService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForumCategoryServiceImpl implements ForumCategoryService {
    private final ForumCategoryRepository forumCategoryRepository;
    private final UserService userService;
    private final Faker faker;
    private final BusinessMapper businessMapper;


    @Override
    @Transactional
    public ForumCategoryDto create(CreateForumCategoryDto createForumCategory) {
        if (forumCategoryRepository.existsByCategoryName(createForumCategory.getCategoryName())) {
            throw new ResourceAlreadyExistsException("Category with name " + createForumCategory.getCategoryName() + " already exists");
        }
        ForumCategoryEntity forumCategoryEntity = ForumCategoryEntity.builder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .backgroundImage(faker.internet().image(640, 200, new Random().toString()))
                .created(LocalDateTime.now())
                .moderator(businessMapper.getUserEntity(createForumCategory.getCreateUserDto()))
                .build();
        return businessMapper.getForumCategory(forumCategoryRepository.save(forumCategoryEntity));
    }

    @Override
    public ForumCategoryDto update(Long categoryId, CreateForumCategoryDto createForumCategory) {
        ForumCategoryEntity forumCategoryEntity = findEntityById(categoryId);
        forumCategoryEntity = forumCategoryEntity.toBuilder()
                .description(createForumCategory.getDescription())
                .categoryName(createForumCategory.getCategoryName())
                .build();
        return businessMapper.getForumCategory(forumCategoryRepository.save(forumCategoryEntity));
    }

    @Override
    public PageDto<ForumCategoryDto> findAll(String moderatorUsername, Integer pageNumber, Integer pageSize) {
        if (!userService.existByName(moderatorUsername)) {
            throw new ResourceNotFoundException("User with username:" + moderatorUsername + " is not found");
        }
        Page<ForumCategoryEntity> categoriesPage = forumCategoryRepository.findAllByModeratorUsername(moderatorUsername, PageRequest.of(pageNumber, pageSize));
        List<ForumCategoryDto> categoryDtos = businessMapper.collectionToList(categoriesPage.getContent(), businessMapper.categoryEntityToDto);
        return PageDto.<ForumCategoryDto>builder()
                .data(categoryDtos)
                .page(pageNumber)
                .size(pageSize)
                .totalSize(categoriesPage.getTotalElements())
                .totalPage(categoriesPage.getTotalPages())
                .build();
    }

    @Override
    public List<ForumCategoryDto> findAll() {
        return businessMapper.collectionToList(forumCategoryRepository.findAll(), businessMapper.categoryEntityToDto);
    }

    @Override
    public void deleteByName(String categoryName) {
        if (!forumCategoryRepository.existsByCategoryName(categoryName)) {
            throw new ResourceAlreadyExistsException("Category with name " + categoryName + " is not found");
        }
        forumCategoryRepository.deleteByCategoryName(categoryName);
    }

    @Override
    public ForumCategoryDto findByName(String categoryName) {
        ForumCategoryEntity forumCategoryEntity = forumCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name:" + categoryName + " is not found"));
        return businessMapper.getForumCategory(forumCategoryEntity);
    }

    @Override
    public ForumCategoryDto findById(Long id) {
        ForumCategoryEntity forumCategoryEntity = forumCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id:" + id + " is not found"));
        return businessMapper.getForumCategory(forumCategoryEntity);
    }


    public ForumCategoryEntity findEntityById(Long id) {
        return forumCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id:" + id + " is not found"));
    }
}
