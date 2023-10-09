package com.spring.labs.lab2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	private String author;
	@NotNull
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9. ]+$")
	private String name;
	@NotNull
	@NotBlank
	@Size(max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9.\n ]+$")
	private String description;
	@NotNull
	@NotBlank
	@Size(min = 1)
	@Pattern(regexp = "^[a-zA-Z0-9.\n ]+$")
	private String content;
}
