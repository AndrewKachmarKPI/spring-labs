package com.spring.labs.lab6.repositories;

import com.spring.labs.lab6.domain.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long> {
    Boolean existsByName(String name);

    @Query(value = "SELECT * FROM post_entity WHERE name = :name", nativeQuery = true)
    Optional<PostEntity> findByName(@Param("name") String name);

    List<PostEntity> findAll();

    List<PostEntity> findByTopicTitle(String topicTitle);

    void deleteByName(String name);

    Optional<PostEntity> findById(Long id);
}
