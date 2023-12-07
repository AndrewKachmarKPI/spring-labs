package com.spring.labs.lab6.service;

import com.spring.labs.lab6.domain.PostEntity;
import com.spring.labs.lab6.dto.PostDto;
import com.spring.labs.lab6.dto.create.CreatePostDto;
import net.datafaker.Faker;

import java.util.List;

public interface PostService {

    PostDto update(CreatePostDto post, Long id);

    void deleteByName(String postName);

    PostDto findById(Long id);

    List<PostDto> findByTopicName(String topicName);

    PostDto findByPostName(String name);

    PostDto createPost(CreatePostDto post, String topicTitle);

    List<PostDto> findAll();
}
