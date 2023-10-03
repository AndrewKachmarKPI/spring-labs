package com.spring.labs.lab2.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreatePostDto {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]+$")
	private final String author;
//	private final Topic topic

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]+$")
	private final String name;

    @NotNull
    @NotBlank 
	private final String content;

    @NotNull
    @NotBlank 
	private final LocalDate creationDate;

    @NotNull
    @NotBlank 
	private final int upvotes;

    @NotNull
    @NotBlank 
	private final int downvotes;
}
