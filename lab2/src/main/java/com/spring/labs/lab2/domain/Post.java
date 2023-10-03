package com.spring.labs.lab2.domain;
 
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class Post {

	private final Long id;
	private final User author;
//	private final Topic topic
	private final String name;
	private final String content;
	private final LocalDateTime creationDate;
	private final int upvotes;
	private final int downvotes;
}
