package com.spring.labs.lab6.mapper;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.dto.ForumCategoryDto;
import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.dto.create.CreateUserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
public class BusinessMapper {
    public Function<ForumCategoryEntity, ForumCategoryDto> categoryEntityToDto = this::getForumCategory;
    public Function<UserEntity, UserDto> userEntityToDto = this::getUserDto;


    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(mapper).toList();
    }

    public ForumCategoryDto getForumCategory(ForumCategoryEntity category) {
        return ForumCategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .backgroundImage(category.getBackgroundImage())
                .moderator(getUserDto(category.getModerator()))
                .created(category.getCreated())
                .build();
    }

    public UserDto getUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .profilePicture(user.getProfilePicture())
                .biography(user.getBiography())
                .registrationDate(user.getRegistrationDate())
                .role(user.getRole())
                .build();
    }

    public UserEntity getUserEntity(UserDto createUserDto) {
        return UserEntity.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .profilePicture(createUserDto.getProfilePicture())
                .biography(createUserDto.getBiography())
                .registrationDate(createUserDto.getRegistrationDate())
                .role(createUserDto.getRole())
                .build();
    }

    public UserEntity getUserEntity(CreateUserDto createUserDto) {
        return UserEntity.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .profilePicture(createUserDto.getProfilePicture())
                .biography(createUserDto.getBiography())
                .registrationDate(LocalDateTime.now())
                .role(createUserDto.getRole())
                .build();
    }
}
