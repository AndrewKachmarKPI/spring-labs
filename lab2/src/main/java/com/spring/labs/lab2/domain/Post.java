package com.spring.labs.lab2.domain;

import java.time.LocalDate; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class Post {
	
	private Long id;
	private User author;
	private ForumCategory forumCategory;
	private String name;
	private String content;
	private LocalDate creationDate;
	private int upvotes;
	private int downvotes;
}
