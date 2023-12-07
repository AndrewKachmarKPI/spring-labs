package com.spring.labs.lab5.service;

import java.util.List;

import com.spring.labs.lab5.domain.Post;
import com.spring.labs.lab5.dto.CreatePostDto;
import net.datafaker.Faker;

public interface PostService {

    Post update(CreatePostDto post, Long id);

    void deleteByName(String postName);

    Post findById(Long id);

    void generateDefaultPosts(Integer size, Faker faker);

    List<Post> findByTopicName(String topicName);

    Post findByPostName(String name);

    Post createPost(CreatePostDto post, String topicTitle);

    List<Post> findAll();
}
