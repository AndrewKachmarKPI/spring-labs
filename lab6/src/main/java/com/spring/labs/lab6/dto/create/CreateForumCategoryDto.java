package com.spring.labs.lab6.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateForumCategoryDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9. ]+$")
    private String categoryName;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    @Pattern(regexp = "^[a-zA-Z0-9.\n ]+$")
    private String description;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]+$")
    private String username;
    private CreateUserDto createUserDto;
}
