package com.spring.labs.lab6.dto.create;

import com.spring.labs.lab6.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateUserDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]+$")
    private final String username;
    @NotNull
    @NotBlank
    @Email
    private final String email;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    @NotBlank
    private final String profilePicture;
    @NotNull
    @NotBlank
    private final String biography;
    @NotNull
    private final UserRole role;
}
