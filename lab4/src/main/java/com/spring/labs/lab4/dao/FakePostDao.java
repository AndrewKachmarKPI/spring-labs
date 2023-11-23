package com.spring.labs.lab4.dao;


import java.util.*;

import com.spring.labs.lab4.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import com.spring.labs.lab4.domain.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FakePostDao implements PostDao {

    private static int generalId=1;
    private final Map<String, Post> posts = new HashMap<>();


    @Override
    public Post save(Post post) {
        if (Optional.ofNullable(post.getId()).isEmpty()) {
            post = post.toBuilder().id((long) generalId ++).build();
        } else {
            Post finalPost = post;
            Post postById = findById(post.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Post with " + finalPost.getId() + " id is not found"));
            posts.remove(postById.getName());
        }
        return posts.put(post.getName(), post);
    }

    @Override
    public List<Post> findAll() {
        return posts.values().stream().sorted(Comparator.comparing(Post::getCreationDate).reversed()).toList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.values().stream().filter(post -> post.getId().equals(id)).findAny()
                .orElseThrow(() -> new RuntimeException("Post with id:" + id + " is not found")));
    }

    @Override
    public Optional<Post> findByName(String name) {
        if (!existByName(name)) {
            throw new ResourceNotFoundException("Post with name " + name + " is not found");
        }
        return Optional.ofNullable(posts.get(name));
    }

    @Override
    public void deleteByName(String postName) {
        if (!existByName(postName)) {
            throw new RuntimeException("Post with " + postName + " name  is not found");
        }
        posts.remove(postName);
    }

    @Override
    public boolean existByName(String postName) {
        return posts.containsKey(postName);
    }
}
