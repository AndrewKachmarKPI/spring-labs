package com.spring.labs.lab2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.labs.lab2.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}