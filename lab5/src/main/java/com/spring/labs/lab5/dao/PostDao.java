package com.spring.labs.lab5.dao;


import com.spring.labs.lab5.domain.Post;

import java.util.List;
import java.util.Optional;

public interface  PostDao {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long id);

    Optional<Post> findByName(String name);

    boolean existByName(String name);

    void deleteByName(String postName);

}
