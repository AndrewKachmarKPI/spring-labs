package com.spring.labs.lab6.repositories;

import com.spring.labs.lab6.domain.TopicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends CrudRepository<TopicEntity, Long> {
    Boolean existsByTitle(String title);

    @Query(value = "SELECT * FROM topic_entity WHERE title = :title", nativeQuery = true)
    Optional<TopicEntity> findByName(@Param("title") String title);

    List<TopicEntity> findAll();

    List<TopicEntity> findAllByForumCategoryCategoryName(String categoryName);

    void deleteByTitle(String title);

    Optional<TopicEntity> findByTopicId(Long id);
}
