package com.spring.labs.lab2.dto;

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
public class CreateTopicDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9. ]+$")
    private String title;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    @Pattern(regexp = "^[a-zA-Z0-9.\n ]+$")
    private String content;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]+$")
    private String author;
}
