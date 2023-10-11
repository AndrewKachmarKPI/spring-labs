package com.spring.labs.lab6.dto;

import com.spring.labs.lab6.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public final class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final String profilePicture;
    private final String biography;
    private final LocalDateTime registrationDate;
    private final UserRole role;
}
