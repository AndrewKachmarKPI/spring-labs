package com.spring.labs.lab6.dto.create;

import com.spring.labs.lab6.dto.TopicDto;
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
public class CreatePostDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.\\s]+$")
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    @Pattern(regexp = "^[a-zA-Z0-9.\\n\\s]+$")
    private String content;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.\\n\\s]+$")
    private String description;

    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9. ]+$")
    private String topicTitle;
    @NotNull
    @NotBlank
    private String author;
}
