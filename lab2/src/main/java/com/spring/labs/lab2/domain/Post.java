package com.spring.labs.lab2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class Post {

	private Long id;
	private User author;
	private Topic topic;
	private String name;
	private String content;
	private String description;
	private LocalDateTime creationDate;
	private int upvotes;
	private int downvotes;

	public void setId(long leastSignificantBits) {
		this.id = leastSignificantBits;
	}
}
