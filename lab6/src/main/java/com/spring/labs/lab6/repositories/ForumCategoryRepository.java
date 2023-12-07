package com.spring.labs.lab6.repositories;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumCategoryRepository extends CrudRepository<ForumCategoryEntity, Long>, PagingAndSortingRepository<ForumCategoryEntity, Long> {
    Boolean existsByCategoryName(String categoryName);

    @Query(value = "SELECT * FROM forum_category_entity WHERE category_name = :categoryName", nativeQuery = true)
    Optional<ForumCategoryEntity> findByName(@Param("categoryName") String categoryName);

    Page<ForumCategoryEntity> findAllByCategoryNameContaining(String categoryName, Pageable pageable);

    List<ForumCategoryEntity> findAll();

    void deleteByCategoryName(String categoryName);

    Optional<ForumCategoryEntity> findCategoryById(Long id);
}
