package com.spring.labs.lab6.repositories;

import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.enums.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("select case when count(u)> 0 then true else false end from UserEntity u where lower(u.username) = lower(:username)")
    Boolean existsByUsername(@Param("username") String username);

    Optional<UserEntity> findByUsername(String username);

    UserEntity findByEmailAndRole(String email, UserRole userRole);
    List<UserEntity> findAll();
}
