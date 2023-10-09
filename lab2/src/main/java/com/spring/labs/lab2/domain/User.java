package com.spring.labs.lab2.domain;

import com.spring.labs.lab2.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public final class User {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final String profilePicture;
    private final String biography;
    private final String registrationDate;
    private final UserRole role;
}