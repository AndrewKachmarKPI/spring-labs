package com.spring.labs.lab6.domain;

import com.spring.labs.lab6.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@NamedQuery(name = "UserEntity.findByEmailAndRole", query = "SELECT u FROM UserEntity u WHERE u.email = ?1 AND u.role=?2")
@NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u")
public final class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String profilePicture;
    @Column(nullable = false)
    private String biography;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
