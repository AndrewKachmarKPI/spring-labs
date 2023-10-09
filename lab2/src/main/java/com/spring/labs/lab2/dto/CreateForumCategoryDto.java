package com.spring.labs.lab2.dto;

import jakarta.validation.constraints.*;
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
    @Size( max = 500)
    @Pattern(regexp = "^[a-zA-Z0-9.\n ]+$")
    private String description;
    @NotNull
    @NotBlank
    private String username;
}
