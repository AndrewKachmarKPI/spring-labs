package com.spring.labs.lab4.service;

import com.spring.labs.lab4.domain.Post;
import com.spring.labs.lab4.dto.CreatePostDto;
import net.datafaker.Faker;

import java.util.List;

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
